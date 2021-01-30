package com.example1.library.controller;

import com.example1.library.model.dto.ExemplaireDTO;
import com.example1.library.model.dto.PretDTO;
import com.example1.library.service.PretService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/pret")
public class PretController {

    @Autowired
    private PretService pretService;

    private static int nombreProlonge = 0;

    private static final Logger logger = LogManager.getLogger(PretController.class);

    @GetMapping("/list")
    public String getAllPrets(Model model) throws IOException, InterruptedException {
        List<PretDTO> ListPretsDto = this.pretService.getAllPrets();
        model.addAttribute("prets", ListPretsDto);
        return "listPrets";
    }

    @GetMapping("/listPretsDispo")
    public String getAllPretsDispo(Model model) throws IOException, InterruptedException {
        List<PretDTO> ListPretsDto = this.pretService.getAllPretsDispo();
        model.addAttribute("pretsdispo", ListPretsDto);
        return "listPretsDispo";
    }

    @GetMapping("/listPretEmprunteOrProlonge")
    public String getAllPretsEmprunteOrProlonge(Model model) throws IOException, InterruptedException {
        List<PretDTO> ListPretsDto = this.pretService.getAllPretsRetourneOrProlonge();
        model.addAttribute("pretsemprunteorprolonge", ListPretsDto);
        return "listPretsEmprunteOrProlonge";
    }


    @RequestMapping(value = "/makePretEmprunte/{id}", method = RequestMethod.GET)
    public String makePretEmprunte(Model model, @PathVariable(value = "id") Long id) throws IOException, InterruptedException {
        model.addAttribute("id", id);
        model.addAttribute("pretsemprunteorprolonge", this.pretService.getPretById(id));
        return "listPretsEmprunteOrProlonge";
    }


    @RequestMapping(value = "/savePretEmprunte/{id}", method = RequestMethod.POST)
    public String savePretEmprunte(@ModelAttribute PretDTO pretId, Model model, @PathVariable(value = "id") Long id, BindingResult result) throws IOException, InterruptedException {
        pretId = this.pretService.getPretById(id);
        logger.info("avant pretId: " + pretId);
        pretId.setDisponible(Boolean.FALSE);
        pretId.setEmprunte(Boolean.TRUE);
        pretId.setProlonge(Boolean.FALSE);
        pretId.setRetourne(Boolean.FALSE);
        pretId.setNombreProlonge(nombreProlonge);
        logger.info("apres pretId: " + pretId);
        this.pretService.savePretEmprunte(pretId, id);
        model.addAttribute("pretsemprunteorprolonge", this.pretService.getAllPretsRetourneOrProlonge());
        return "redirect:/pret/listPretEmprunteOrProlonge";

    }

    @RequestMapping(value = "/makePretProlonge/{id}", method = RequestMethod.GET)
    public String makePretProlonge(Model model, @PathVariable(value = "id") Long id) throws IOException, InterruptedException {
        model.addAttribute("id", id);
        model.addAttribute("pretsemprunteorprolonge", this.pretService.getPretById(id));
        return "listPretsEmprunteOrProlonge";
    }


    @RequestMapping(value = "/savePretProlonge/{id}", method = RequestMethod.POST)
    public String savePretProlonge(@ModelAttribute PretDTO pretId, Model model, @PathVariable(value = "id") Long id) throws IOException, InterruptedException {

        pretId = this.pretService.getPretById(id);
        pretId.setDisponible(Boolean.FALSE);
        pretId.setEmprunte(Boolean.FALSE);
        pretId.setProlonge(Boolean.TRUE);
        pretId.setRetourne(Boolean.FALSE);
        logger.info("nombre prolonge: " + nombreProlonge);
        logger.info("apres pretId 2: " + pretId);
        if (nombreProlonge == 0) {
            nombreProlonge = nombreProlonge + 1;
            logger.info("nombre prolonge 2: " + nombreProlonge);
            pretId.setNombreProlonge(nombreProlonge);
            this.pretService.savePretProlonge(pretId, id);
        }
        model.addAttribute("pretsemprunteorprolonge", this.pretService.getAllPretsRetourneOrProlonge());
        return "redirect:/pret/listPretEmprunteOrProlonge";
    }

    @RequestMapping(value = "/makePretRetourne/{id}", method = RequestMethod.GET)
    public String makePretRetourne(Model model, @PathVariable(value = "id") Long id) throws IOException, InterruptedException {
        model.addAttribute("id", id);
        model.addAttribute("pret", this.pretService.getPretById(id));
        return "listPretsEmprunteOrProlonge";
    }


    @RequestMapping(value = "/savePretRetourne/{id}", method = RequestMethod.POST)
    public String savePretRetourne(@ModelAttribute PretDTO pretId, Model model, @PathVariable(value = "id") Long id) throws IOException, InterruptedException {
        pretId = this.pretService.getPretById(id);
        pretId.setDisponible(Boolean.TRUE);
        pretId.setEmprunte(Boolean.FALSE);
        pretId.setProlonge(Boolean.FALSE);
        pretId.setNombreProlonge(0);
        pretId.setRetourne(Boolean.TRUE);
        this.pretService.savePretRetourne(pretId, id);
        model.addAttribute("prets", this.pretService.getAllPrets());
        return "redirect:/pret/listPretEmprunteOrProlonge";
    }

/*   @RequestMapping(value = "/livreList", method = RequestMethod.GET)
    public String listLivreSearch(Model model) throws IOException, InterruptedException {
        model.addAttribute("pret", new PretDTO());
        logger.info(new PretDTO());
        return "livreSearch";
    }

    @RequestMapping(value = "/SearchLivreList", method = RequestMethod.POST)
    public String saveLivreSearchList(Model model, PretDTO pretEnrecherche) throws IOException, InterruptedException {
        String auteur = pretEnrecherche.getExemplaire().getLivre().getAuteur();
        String titre = pretEnrecherche.getExemplaire().getLivre().getTitre();
    //    String recherche = (auteur);
        String recherche1 = titre;
        List<PretDTO> livreRecherche = this.pretService.findLivreByAuteurOrTitre(recherche);
        logger.info(this.pretService.findLivreByAuteurOrTitre(recherche,recherche1));
        if (recherche1 != null) {
            model.addAttribute("livresearch", this.pretService.findLivreByAuteurOrTitre(recherche,recherche1));
        }
        return "searchListLivre";
    }*/

}
