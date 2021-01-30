package com.example1.library.controller;

import com.example1.library.model.dto.ExemplaireDTO;
import com.example1.library.model.dto.LivreDTO;
import com.example1.library.model.dto.UtilisateurDTO;
import com.example1.library.service.ExemplaireService;
import com.example1.library.service.LivreService;
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
@RequestMapping("/livre")
public class LivreController {

    @Autowired
    private LivreService livreService;

    @Autowired
    private ExemplaireService exemplaireService;

    private static final Logger logger = LogManager.getLogger(LivreController.class);

    @GetMapping("/list")
    public String getAllLivres(Model model) throws IOException,InterruptedException{
        List<LivreDTO> ListLivresDto = this.livreService.getAllLivres();
        model.addAttribute("livres", ListLivresDto);
        return "listLivres";
    }

    @GetMapping("/listExemplaireByLivre/{id}")
    public String findExemplaireById(@PathVariable(value = "id") Long id,Model model) throws IOException,InterruptedException {
        List<ExemplaireDTO> ListExemplairesDto = this.exemplaireService.findExemplairesByLivre(id);
        model.addAttribute("exemplaires", ListExemplairesDto);
        return "listExemplairesByLivre";
    }

    /**
     * Méthode permet d'ajouter l'utilisateur en get
     *
     * @param model
     * * @return la page "addUtilisateur"
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String ajouterlivre(Model model) {
        model.addAttribute("livre", new LivreDTO());
        return "addLivre";
    }

    /**
     * Méthode permet d'ajouter l'utilisateur' en post
     *
     * @param model
     * @param livre
     * @param result
     * * @return la page "listUtilisateur"
     */
    @RequestMapping(value = "/saveLivre", method = RequestMethod.POST)
    public String saveLivre(@Valid @ModelAttribute LivreDTO livre, Model model, BindingResult result) throws IOException,InterruptedException
    {
        if (result.hasErrors()) {
            return "addLivre";
        } else {
            this.livreService.saveLivre(livre);
            model.addAttribute("livres", this.livreService.getAllLivres());
            return "redirect:/livre/list";
        }
    }

    @GetMapping(value = "/details/{id}")
    public String detailLivre(@PathVariable(value = "id") Long id, Model model) throws IOException,InterruptedException
    {
        System.out.println(id);
        LivreDTO livre = livreService.getLivreById(id);
        System.out.println(livre);
        if (livre == null) {
            logger.info("le livre n'existe pas");
        }
        logger.info(this.livreService.getLivreById(id));
        model.addAttribute("livre", this.livreService.getLivreById(id));
        return "detailsLivre";
    }

    /**
     * Méthode permet de modifier l'utilisateur en get
     *
     * @param model
     * @param id
     * @return la page "editionUser"
     */
    @RequestMapping(value = "/editionLivre/{id}", method = RequestMethod.GET)
    public String editionLivre(@PathVariable(value = "id") Long id, Model model) throws IOException,InterruptedException
    {
        model.addAttribute("livre", this.livreService.getLivreById(id));
        return "editionLivre";

    }

    /**
     * Méthode permet de modifier l'utilisateur en post
     *
     * @param model
     * @param id
     * @param livre
     * @return la page "editionUser"
     */
    @RequestMapping(value = "/editionLivre/{id}", method = RequestMethod.POST)
    public String editionlivre(@PathVariable(value = "id") long id, @ModelAttribute LivreDTO livre,BindingResult errors, Model model) throws IOException,InterruptedException
    {
        if (errors.hasErrors()) {
            return "editionlivre";
        } else {
            LivreDTO livreId = this.livreService.getLivreById(id);
            livreId.setTitre(livre.getTitre());
            livreId.setAuteur(livre.getAuteur());
            livreId.setCategorie(livre.getCategorie());
            this.livreService.updateLivre(livreId,id);
            model.addAttribute("livres", this.livreService.getAllLivres());
            return "redirect:/livre/list";
        }
    }

    /**
     * Méthode permet d'ajouter l'exemplaire en get
     *
     * @param model
     * * @return la page "addExemplaire"
     */
    @RequestMapping(value = "/addExemplaire/{id}", method = RequestMethod.GET)
    public String ajouterExemplaire(Model model,@PathVariable("id") Long id) throws IOException,InterruptedException {
        LivreDTO livreId = this.livreService.getLivreById(id);
        model.addAttribute("livreId", livreId);
        model.addAttribute("exemplaire", new ExemplaireDTO());
        return "addExemplaire";
    }


    @RequestMapping(value = "/saveExemplaire/{id}", method = RequestMethod.POST)
    public String saveExemplaire(@Valid @ModelAttribute ExemplaireDTO exemplaire, Model model, BindingResult result,@PathVariable("id") Long id) throws IOException,InterruptedException
    {
        if (result.hasErrors()) {
            return "addExemplaire";
        } else {
            LivreDTO livreId = this.livreService.getLivreById(id);
            ExemplaireDTO exemplaireAjoute = new ExemplaireDTO();
            exemplaireAjoute.setCollection(exemplaire.getCollection());
            exemplaireAjoute.setEditeur(exemplaire.getEditeur());
            exemplaireAjoute.setIsbn(exemplaire.getIsbn());
            exemplaireAjoute.setDescription(exemplaire.getDescription());
            exemplaireAjoute.setNombre(exemplaire.getNombre());
            logger.info("livreId :" +livreId );
            exemplaireAjoute.setLivre(livreId );
            this.exemplaireService.saveExemplaire(exemplaireAjoute,id);
            model.addAttribute("exemplaires", this.exemplaireService.getAllExemplaires());
            return "redirect:/exemplaire/list";
        }
    }

/*    @RequestMapping(value = "/livreList", method = RequestMethod.GET)
    public String listLivreSearch(Model model) throws IOException, InterruptedException {
        model.addAttribute("livre", new LivreDTO());
        logger.info(new LivreDTO());
        return "livreSearch";
    }

    @RequestMapping(value = "/SearchLivreList", method = RequestMethod.POST)
    public String saveLivreSearchList(Model model, LivreDTO livreEnrecherche) throws IOException, InterruptedException {
        String auteur = livreEnrecherche.getAuteur();
        String titre = livreEnrecherche.getTitre();
        String recherche = (auteur);
        String recherche1 = titre;
        List<LivreDTO> livreRecherche = this.livreService.findLivreByAuteurOrTitre(recherche,recherche1);
        logger.info(this.livreService.findLivreByAuteurOrTitre(recherche,recherche1));
        if (recherche1 != null) {
            model.addAttribute("livresearch", this.livreService.findLivreByAuteurOrTitre(recherche,recherche1));
        }
        return "searchListLivre";
    }*/


}
