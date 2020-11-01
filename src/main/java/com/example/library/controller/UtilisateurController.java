package com.example.library.controller;

import com.example.library.model.dto.PretDTO;
import com.example.library.model.dto.UtilisateurDTO;
import com.example.library.model.entity.Pret;
import com.example.library.model.entity.Utilisateur;
import com.example.library.repository.RoleRepository;
import com.example.library.repository.UtilisateurRepository;
import com.example.library.service.UtilisateurService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/utilisateur")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UtilisateurRepository utilisateurRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;
    

    @GetMapping("/list")
    public String getAllUtilisateurs(Model model) {
        List<UtilisateurDTO> ListUtilisateursDto = this.utilisateurService.getAllUsers();
        model.addAttribute("utilisateurs", ListUtilisateursDto);
        return "listUtilisateurs";
    }

    private Utilisateur convertToEntity(UtilisateurDTO utilisateurDTO) throws ParseException {
        ModelMapper mapper = new ModelMapper();
        Utilisateur utilisateur = mapper.map(utilisateurDTO, Utilisateur.class);

        return utilisateur;
    }


}
