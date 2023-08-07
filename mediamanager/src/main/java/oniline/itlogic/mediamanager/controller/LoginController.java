package oniline.itlogic.mediamanager.controller;

import oniline.itlogic.mediamanager.model.Benutzerprofil;
import oniline.itlogic.mediamanager.model.LoginRequest;
import oniline.itlogic.mediamanager.service.BenutzerprofilService;
import oniline.itlogic.mediamanager.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;


@Controller
public class LoginController {
    private final BenutzerprofilService benutzerprofilService;
    @Autowired
    public LoginController(BenutzerprofilService benutzerprofilService) {
        this.benutzerprofilService = benutzerprofilService;
    }
    @GetMapping("/login")
    public String showLogin(){
        return "login";
    }


}
