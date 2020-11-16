package com.example.library.controller;

import com.example.library.model.dto.PretDTO;
import com.example.library.model.dto.UtilisateurDTO;
import com.example.library.model.entity.Pret;
import com.example.library.model.entity.Utilisateur;
import com.example.library.service.UtilisateurService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/utilisateur")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping("/list")
    public String getAllUtilisateurs(Model model) throws IOException,InterruptedException {
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
