package oniline.itlogic.mediamanager.security.config;

import lombok.AllArgsConstructor;
import oniline.itlogic.mediamanager.model.Benutzerprofil;
import oniline.itlogic.mediamanager.service.BenutzerprofilService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final BenutzerprofilService benutzerprofilService;

    public WebSecurityConfig(BenutzerprofilService benutzerprofilService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.benutzerprofilService = benutzerprofilService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    private final BCryptPasswordEncoder bCryptPasswordEncoder;



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors() // Enable CORS
                .and()
                .authorizeRequests()
                .antMatchers("/api/v*/registration/**" ,"/api/find/**","/api/categories/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginPage("/login") // Define the URL for your custom login page
                .successHandler((request, response, authentication) -> {
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    Benutzerprofil userDetails = (Benutzerprofil) authentication.getPrincipal();
                    long id = userDetails.getId(); // Hier gehen wir davon aus, dass Sie eine getId()-Methode in der Benutzerprofil-Klasse haben.
                    String email = userDetails.getEmail();
                    String benutzername = userDetails.getBenutzername();
                    String role = userDetails.getBenutzerRole().toString();
                    String responseBody = "{\"message\": \"Login successful\"," +
                            " \"id\": \"" + id + "\"," +
                            " \"email\": \"" + email + "\"," +
                            " \"benutzername\": \"" + benutzername + "\"," +
                            " \"benutzerrole\": \"" + role + "\"" +
                            "}";
                    System.out.println("Login successful"+responseBody);
                    response.getWriter().write(responseBody);
                })
                .failureHandler((request, response, exception) -> {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    String errorMessage = "Authentication failure";
                    String responseBody = "{\"error\": \"" + errorMessage + "\"}";
                    System.out.println("Authentication failure");
                    response.getWriter().write(responseBody);
                })
                .permitAll()
                .and()
                .logout()
                .logoutSuccessHandler((HttpServletRequest request, HttpServletResponse response, Authentication authentication)->{
                    System.out.println("User ist ausgeloggt"); // Log the message to the server console
                    new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK).onLogoutSuccess(request, response, authentication);
                }) // Custom logout success handler
                .permitAll();

    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**").and().ignoring().antMatchers("/api/v*/registration/**");
        web.ignoring().antMatchers("/img/**").and().ignoring().antMatchers("/api/v*/registration/**");
        web.ignoring().antMatchers("/js/**").and().ignoring().antMatchers("/api/v*/registration/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(benutzerprofilService);
        return provider;
    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*") // Erlaubte Header für CORS-Anfragen
                        .allowCredentials(true) // Erlaubt das Senden von Cookies in CORS-Anfragen
                        .maxAge(3600); // Maximalalter der CORS-Informationen im Cache des Browsers
            }
        };
    }

}
