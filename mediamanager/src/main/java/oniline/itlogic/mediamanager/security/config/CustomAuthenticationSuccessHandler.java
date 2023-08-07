package oniline.itlogic.mediamanager.security.config;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // Hier kannst du die gewünschten Aktionen nach erfolgreicher Anmeldung durchführen
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        String username = authentication.getName();
        String responseBody = "{\"message\": \"Login successful\", \"username\": \"" + username + "\"}";
        System.out.println("Login successful");
        response.getWriter().write(responseBody);
    }
}
