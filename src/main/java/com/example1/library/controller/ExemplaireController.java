package com.example1.library.controller;

import com.example1.library.model.dto.ExemplaireDTO;
import com.example1.library.model.dto.LivreDTO;
import com.example1.library.model.dto.PretDTO;
import com.example1.library.service.ExemplaireService;
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
@RequestMapping("/exemplaire")
public class ExemplaireController {

    @Autowired
    private ExemplaireService exemplaireService;

    @Autowired
    private PretService pretService;

    private static final Logger logger = LogManager.getLogger(ExemplaireController.class);

    @GetMapping("/list")
    public String getAllExemplaires(Model model) throws IOException,InterruptedException {
        List<ExemplaireDTO> ListExemplairesDto = this.exemplaireService.getAllExemplaires();
        logger.info("list exemplaires :" + ListExemplairesDto);
        model.addAttribute("exemplaires", ListExemplairesDto);
        return "listExemplaires";
    }


    @GetMapping(value = "/details/{id}")
    public String detail(@PathVariable(value = "id") Long id, Model model) throws IOException,InterruptedException
    {
        ExemplaireDTO exemplaire = exemplaireService.getExemplaireById(id);
        if (exemplaire == null) {
            logger.info("l'utilisateur n'existe pas");
        }
        logger.info(this.exemplaireService.getExemplaireById(id));
        model.addAttribute("exemplaire", this.exemplaireService.getExemplaireById(id));
        return "detailsExemplaire";
    }

    /**
     * Méthode permet de modifier l'utilisateur en get
     *
     * @param model
     * @param id
     * @return la page "editionUser"
     */
    @RequestMapping(value = "/editionExemplaire/{id}", method = RequestMethod.GET)
    public String editionExemplaire(@PathVariable(value = "id") Long id, Model model) throws IOException,InterruptedException
    {
        model.addAttribute("exemplaire", this.exemplaireService.getExemplaireById(id));
        return "editionExemplaire";

    }

    /**
     * Méthode permet de modifier l'utilisateur en post
     *
     * @param model
     * @param id
     * @param exemplaire
     * @return la page "editionUser"
     */
    @RequestMapping(value = "/editionExemplaire/{id}", method = RequestMethod.POST)
    public String editionExemplaire(@PathVariable(value = "id") long id, @ModelAttribute ExemplaireDTO exemplaire, BindingResult errors, Model model) throws IOException,InterruptedException
    {
        if (errors.hasErrors()) {
            return "editionExemplaire";
        } else {
            ExemplaireDTO exemplaireId = this.exemplaireService.getExemplaireById(id);
            exemplaireId.setCollection(exemplaire.getCollection());
            exemplaireId.setEditeur(exemplaire.getEditeur());
            exemplaireId.setIsbn(exemplaire.getIsbn());
            exemplaireId.setDescription(exemplaire.getDescription());
            exemplaireId.setNombre(exemplaire.getNombre());
            this.exemplaireService.updateExemplaire(exemplaire,id);
            model.addAttribute("exemplaires", this.exemplaireService.getAllExemplaires());
            return "redirect:/exemplaire/list";
        }
    }

    /**
     * Méthode permet d'ajouter l'exemplaire en get
     *
     * @param model
     * * @return la page "addExemplaire"
     */
    @RequestMapping(value = "/addPretDispo/{id}", method = RequestMethod.GET)
    public String ajouterPret(Model model,@PathVariable(value = "id") Long id)
            throws IOException,InterruptedException {
        ExemplaireDTO exemplaireId = this.exemplaireService.getExemplaireById(id);
        logger.info("id: " + id);
        logger.info("id exemplaire: " +exemplaireId);
        model.addAttribute("id", id);
        model.addAttribute("exemplaire", this.exemplaireService.getExemplaireById(id));
        model.addAttribute("pret", new PretDTO());
        return "listExemplaires";
    }


    @RequestMapping(value = "/savePretDispo/{id}", method = RequestMethod.POST)
    public String savePret(@PathVariable(value = "id") Long id,Model model, PretDTO pretDispo)
            throws IOException,InterruptedException
    {
            ExemplaireDTO exemplaireId = this.exemplaireService.getExemplaireById(id);
            pretDispo.setExemplaire(exemplaireId);
            pretDispo.setDisponible(Boolean.TRUE);
            pretDispo.setEmprunte(Boolean.FALSE);
            pretDispo.setProlonge(Boolean.FALSE);
            pretDispo.setRetourne(Boolean.FALSE);
            pretDispo.setNombreProlonge(0);
            System.out.println(pretDispo);
            this.pretService.savePret(pretDispo,id);
            model.addAttribute("prets", this.pretService.getAllPrets());
            return "redirect:/pret/listPretsDispo";
    }


}
