package com.example.library.controller;

import com.example.library.model.dto.LivreDTO;
import com.example.library.model.dto.UtilisateurDTO;
import com.example.library.model.entity.Livre;
import com.example.library.model.entity.Utilisateur;
import com.example.library.service.ExemplaireService;
import com.example.library.service.LivreService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/livre")
public class LivreController {

    @Autowired
    private LivreService livreService;

    @Autowired
    private ExemplaireService exemplaireService;

    @GetMapping("/list")
    public String getAllLivres(Model model) {
        List<LivreDTO> ListLivresDto = this.livreService.getAllLivres();
        model.addAttribute("livres", ListLivresDto);
        return "listLivres";
    }
    //
    private Livre convertToEntity(LivreDTO livreDTO) throws ParseException {
        ModelMapper mapper = new ModelMapper();
        Livre livre = mapper.map(livreDTO, Livre.class);

        return livre;
    }

}
