package com.example.library.controller;
import com.example.library.model.dto.PretDTO;
import com.example.library.model.entity.Pret;
import com.example.library.service.PretService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/pret")
public class PretController {

    @Autowired
    private PretService pretService;

    @GetMapping("/list")
    public String getAllPrets(Model model) throws IOException,InterruptedException {
        List<PretDTO> ListPretsDto = this.pretService.getAllPrets();
        model.addAttribute("prets", ListPretsDto);
        return "listPrets";
    }

}
