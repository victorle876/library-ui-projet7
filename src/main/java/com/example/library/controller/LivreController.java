package com.example.library.controller;

import com.example.library.model.dto.LivreDTO;
import com.example.library.model.dto.UtilisateurDTO;
import com.example.library.model.entity.Livre;
import com.example.library.model.entity.Utilisateur;
import com.example.library.service.LivreService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/livre")
public class LivreController {

    @Autowired
    private LivreService livreService;

    @GetMapping("/list")
    public String getAllLivres(Model model) throws IOException,InterruptedException{
        List<LivreDTO> ListLivresDto = this.livreService.getAllLivres();
        model.addAttribute("livres", ListLivresDto);
        return "listLivres";
    }


}
