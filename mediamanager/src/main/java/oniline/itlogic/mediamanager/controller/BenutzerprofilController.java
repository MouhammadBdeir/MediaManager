package oniline.itlogic.mediamanager.controller;

import oniline.itlogic.mediamanager.model.BenutzerRole;
import oniline.itlogic.mediamanager.model.Benutzerprofil;
import oniline.itlogic.mediamanager.model.RegistrationRequest;
import oniline.itlogic.mediamanager.service.BenutzerprofilService;
import oniline.itlogic.mediamanager.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.spring5.SpringTemplateEngine;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("")
public class BenutzerprofilController {
    private final RegistrationService registrationService;
    private final BenutzerprofilService benutzerprofilService;
    private final SpringTemplateEngine templateEngine;
    private final ResourceLoader resourceLoader;

    @Autowired
    public BenutzerprofilController(BenutzerprofilService benutzerprofilService, RegistrationService registrationService, SpringTemplateEngine templateEngine, ResourceLoader resourceLoader) {
        this.registrationService = registrationService;
        this.benutzerprofilService = benutzerprofilService;
        this.templateEngine = templateEngine;
        this.resourceLoader = resourceLoader;
    }
    @GetMapping("api/v1/registration/all")
    public ResponseEntity<List<Benutzerprofil>> getAllBenutzerprofil(){
        List<Benutzerprofil>   benutzerprofil = benutzerprofilService.findAllBenutzerprofil();
        return new ResponseEntity<>( benutzerprofil, HttpStatus.OK);
    }

    @GetMapping("api/v1/registration/find/{id}")
    public ResponseEntity<Benutzerprofil> getBenutzerprofilById(@PathVariable("id") Long id){
        Benutzerprofil benutzerprofil= benutzerprofilService.findBenutzerprofilById(id);
        return new ResponseEntity<>(benutzerprofil, HttpStatus.OK);
    }
    @PostMapping("api/v1/registration/add")
    public ResponseEntity<Map<String, String>> register(@RequestBody RegistrationRequest request) {
        String token = registrationService.register(request);
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    @PutMapping("api/v1/registration/benuzter/update")
    public ResponseEntity<Benutzerprofil> updateBenutzerprofil(@RequestBody Benutzerprofil updatedBenutzerprofil) {
        Benutzerprofil existingBenutzerprofil = benutzerprofilService.findBenutzerprofilById(updatedBenutzerprofil.getId());

        // Überprüfen und aktualisieren Sie alle Attribute, die Sie ändern möchten

        if (updatedBenutzerprofil.getBenutzername() != null) {
            existingBenutzerprofil.setBenutzername(updatedBenutzerprofil.getBenutzername());
        }

        if (updatedBenutzerprofil.getEmail() != null) {
            existingBenutzerprofil.setEmail(updatedBenutzerprofil.getEmail());
        }

        if (updatedBenutzerprofil.getGoogleId() != null) {
            existingBenutzerprofil.setGoogleId(updatedBenutzerprofil.getGoogleId());
        }

        if (updatedBenutzerprofil.getPassword() != null) {
            existingBenutzerprofil.setPassword(updatedBenutzerprofil.getPassword());
        }

        if (updatedBenutzerprofil.getBenutzerRole() != null) {
            existingBenutzerprofil.setBenutzerRole(updatedBenutzerprofil.getBenutzerRole());
        }

        if (updatedBenutzerprofil.getLocked() != null) {
            existingBenutzerprofil.setLocked(updatedBenutzerprofil.getLocked());
        }

        if (updatedBenutzerprofil.getEnabled() != null) {
            existingBenutzerprofil.setEnabled(updatedBenutzerprofil.getEnabled());
        }

        Benutzerprofil savedBenutzerprofil = benutzerprofilService.updateBenutzerprofil(existingBenutzerprofil);

        return new ResponseEntity<>(savedBenutzerprofil, HttpStatus.OK);
    }
    @PostMapping("benuzter/update/{id}")
    public ResponseEntity<Benutzerprofil> updateBenutzerprofil(@PathVariable("id") Long id,@RequestBody Benutzerprofil updatedBenutzerprofil,Authentication authentication) {
        Benutzerprofil existingBenutzerprofil = benutzerprofilService.findBenutzerprofilById(id);

       if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Benutzerprofil userDetails = (Benutzerprofil) authentication.getPrincipal();
        BenutzerRole r = BenutzerRole.ADMIN;
         System.out.println(userDetails);
        if(userDetails.getBenutzerRole()==r){
            if (updatedBenutzerprofil.getBenutzername() != null) {
                existingBenutzerprofil.setBenutzername(updatedBenutzerprofil.getBenutzername());
            }

            if (updatedBenutzerprofil.getEmail() != null) {
                existingBenutzerprofil.setEmail(updatedBenutzerprofil.getEmail());
            }

            if (updatedBenutzerprofil.getGoogleId() != null) {
                existingBenutzerprofil.setGoogleId(updatedBenutzerprofil.getGoogleId());
            }

            if (updatedBenutzerprofil.getPassword() != null) {
                existingBenutzerprofil.setPassword(updatedBenutzerprofil.getPassword());
            }

            if (updatedBenutzerprofil.getBenutzerRole() != null) {
                existingBenutzerprofil.setBenutzerRole(updatedBenutzerprofil.getBenutzerRole());
            }

            if (updatedBenutzerprofil.getLocked() != null) {
                existingBenutzerprofil.setLocked(updatedBenutzerprofil.getLocked());
            }

            if (updatedBenutzerprofil.getEnabled() != null) {
                existingBenutzerprofil.setEnabled(updatedBenutzerprofil.getEnabled());
            }

            Benutzerprofil savedBenutzerprofil = benutzerprofilService.updateBenutzerprofil(existingBenutzerprofil);
            return new ResponseEntity<>(savedBenutzerprofil, HttpStatus.OK);
        }
        else return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("api/v1/registration/delete/{id}")
    public ResponseEntity<?> deleteBenutzerprofil(@PathVariable("id") Long id){
        benutzerprofilService.deleteBenutzerprofil(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping(path = "api/v1/registration/confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

    @GetMapping("profile")
    public ResponseEntity<?> showProfile(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            // User is not authenticated, handle accordingly
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Benutzerprofil userDetails = (Benutzerprofil) authentication.getPrincipal();
        String benutzername = userDetails.getBenutzername();
        String email = userDetails.getEmail();
        BenutzerRole benutzerRole= userDetails.getBenutzerRole();
        Map<String, String> profileData = new HashMap<>();
        profileData.put("benutzername", benutzername);
        profileData.put("email", email);
        profileData.put("benutzerRole", benutzerRole.toString());
        return ResponseEntity.ok(profileData);
    }



}
