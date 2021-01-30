package com.example1.library.controller;

import com.example1.library.service.UtilisateurService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

@Controller
public class HomeController {
    private static final Logger logger = LogManager.getLogger(HomeController.class);

    @Autowired
    private UtilisateurService utilisateurService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) throws IOException, InterruptedException {
        model.addAttribute("utilisateurs", this.utilisateurService.getAllUsers());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("authentication: " + authentication);
        return "home";
    }

    @RequestMapping(value = "/admin/home", method = RequestMethod.GET)
    public String AdminHome(Model model) {
        return "adminhome";
    }

}
