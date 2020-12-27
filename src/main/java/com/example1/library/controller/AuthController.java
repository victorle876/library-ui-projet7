package com.example1.library.controller;

import com.example1.library.model.dto.ExemplaireDTO;
import com.example1.library.model.dto.PretDTO;
import com.example1.library.model.entity.Utilisateur;
import com.example1.library.service.ExemplaireService;
import com.example1.library.service.PretService;
import com.example1.library.service.UtilisateurService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UtilisateurService utilisateurService;

    private static final Logger logger = LogManager.getLogger(AuthController.class);

    @RequestMapping(value = "/connect", method = RequestMethod.GET)
    public String connect(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "connexion";

    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model)  throws IOException,InterruptedException{
        model.addAttribute("utilisateurs", this.utilisateurService.getAllUsers());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info(authentication);
        return "home";
    }

}
