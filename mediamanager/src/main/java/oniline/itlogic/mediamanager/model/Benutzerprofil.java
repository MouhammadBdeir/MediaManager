package oniline.itlogic.mediamanager.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Benutzerprofil implements UserDetails {
    @SequenceGenerator(
            name = "Profile_sequence",
            sequenceName = "Eco_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "Profile_sequence_sequence"
    )
    private Long id;
    private String benutzername;
    private String password;
    private String email;
    private String googleId;
    @Enumerated(EnumType.STRING)
    private BenutzerRole benutzerRole;
    private Boolean locked = false;
    private Boolean enabled = false;

    @ElementCollection
    @CollectionTable(name = "authorities", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "authority")
    private Set<String> authorities = new HashSet<>();

    public Benutzerprofil(String benutzername, String password, String email, String googleId, BenutzerRole benutzerRole) {
        this.benutzername = benutzername;
        this.password = password;
        this.email = email;
        this.googleId = googleId;
        this.benutzerRole = benutzerRole;
    }




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }


    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority(benutzerRole.name());
        return Collections.singletonList(authority);
    }
    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = new HashSet<>();
        for (GrantedAuthority authority : authorities) {
            this.authorities.add(authority.getAuthority());
        }
    }
    public BenutzerRole getBenutzerRole() {
        return benutzerRole;
    }

    public Boolean getLocked() {
        return locked;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public String getEmail() {
        return email;
    }


    public void setPassword(String password) {
        this.password = password;
    }
}
