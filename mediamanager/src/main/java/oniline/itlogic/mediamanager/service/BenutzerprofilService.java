package oniline.itlogic.mediamanager.service;



import lombok.AllArgsConstructor;
import oniline.itlogic.mediamanager.exceptions.UserNotFoundException;
import oniline.itlogic.mediamanager.model.Benutzerprofil;
import oniline.itlogic.mediamanager.model.ConfirmationToken;
import oniline.itlogic.mediamanager.repo.BenutzerprofilRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
@AllArgsConstructor
public class BenutzerprofilService implements UserDetailsService {
    private final static String USER_NOT_FOUND_MSG =
            "user with email %s not found";
    private final BenutzerprofilRepository benutzerprofilRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ConfirmationTokenService confirmationTokenService;
    public List<Benutzerprofil> findAllBenutzerprofil(){
        return benutzerprofilRepository.findAll();
    }
    public Benutzerprofil updateBenutzerprofil(Benutzerprofil benutzerprofil){
        return benutzerprofilRepository.save(benutzerprofil);
    }
    public Benutzerprofil findBenutzerprofilById(Long id){
        return benutzerprofilRepository.findBenutzerprofilById(id).orElseThrow(()-> new UserNotFoundException("Benutzerprofil by id " + id+" was not Found" ));
    }
    public  void deleteBenutzerprofil(Long id){
        benutzerprofilRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return benutzerprofilRepository.findByEmail(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format(USER_NOT_FOUND_MSG, username)));
    }
    public String signUpUser(Benutzerprofil benutzerprofil) {
        boolean userExists = benutzerprofilRepository
                .findByEmail(benutzerprofil.getEmail())
                .isPresent();

        if (userExists) {
            // TODO check of attributes are the same and
            // TODO if email not confirmed send confirmation email.

            throw new IllegalStateException("email already taken");
        }

        String encodedPassword = bCryptPasswordEncoder
                .encode(benutzerprofil.getPassword());

        benutzerprofil.setPassword(encodedPassword);

        benutzerprofilRepository.save(benutzerprofil);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                benutzerprofil
        );

        confirmationTokenService.saveConfirmationToken(
                confirmationToken);

//        TODO: SEND EMAIL

        return token;
    }
    public int enableAppUser(String email) {
        return benutzerprofilRepository.enableAppUser(email);
    }

}