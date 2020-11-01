package com.example.library.controller;

import com.example.library.model.dto.ExemplaireDTO;
import com.example.library.model.dto.PretDTO;
import com.example.library.model.entity.Exemplaire;
import com.example.library.model.entity.Pret;
import com.example.library.service.ExemplaireService;
import com.example.library.service.PretService;
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
@RequestMapping("/api/exemplaire")
public class ExemplaireController {

    @Autowired
    private ExemplaireService exemplaireService;

    @Autowired
    private PretService pretService;

    @GetMapping("/list")
    public String getAllExemplaires(Model model) {
        List<ExemplaireDTO> ListExemplairesDto = this.exemplaireService.getAllExemplaires();
        model.addAttribute("exemplaires", ListExemplairesDto);
        return "listExemplaires";
    }


    private Exemplaire convertToEntity(ExemplaireDTO exemplaireDTO) throws ParseException {
        ModelMapper mapper = new ModelMapper();
        Exemplaire exemplaire = mapper.map(exemplaireDTO, Exemplaire.class);

        return exemplaire;
    }

    private Pret convertToEntity(PretDTO pretDTO) throws ParseException {
        ModelMapper mapper = new ModelMapper();
        Pret pret = mapper.map(pretDTO, Pret.class);

        return pret;
    }
}
