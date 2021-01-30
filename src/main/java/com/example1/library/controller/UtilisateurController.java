package com.example1.library.controller;

import com.example1.library.model.dto.UtilisateurDTO;
import com.example1.library.model.entity.Utilisateur;
import com.example1.library.service.UtilisateurService;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/utilisateur")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    private static final Logger logger = LogManager.getLogger(UtilisateurController.class);

    @GetMapping("/list")
    public String getAllUtilisateurs(Model model) throws IOException, InterruptedException {
        List<UtilisateurDTO> ListUtilisateursDto = this.utilisateurService.getAllUsers();
        model.addAttribute("utilisateurs", ListUtilisateursDto);
        return "listUtilisateurs";
    }

    /**
     * Méthode permet d'ajouter l'utilisateur en get
     *
     * @param model * @return la page "addUtilisateur"
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String ajouterUtilisateur(Model model) {
        System.out.println(new UtilisateurDTO());
        model.addAttribute("utilisateur", new UtilisateurDTO());
        return "addUser";
    }

    /**
     * Méthode permet d'ajouter l'utilisateur' en post
     *
     * @param model
     * @param result * @return la page "listUtilisateur"
     */
    @RequestMapping(value = "/saveUtilisateur", method = RequestMethod.POST)
    public String save(@ModelAttribute UtilisateurDTO utilisateur, Model model, BindingResult result) throws IOException, InterruptedException {
        if (result.hasErrors()) {
            return "addUser";
        } else {
            System.out.println(utilisateur.getUsername());
            this.utilisateurService.saveUser(utilisateur);
            model.addAttribute("utilisateurs", this.utilisateurService.getAllUsers());
            return "redirect:/utilisateur/list";
        }
    }

    @GetMapping(value = "/detailsUser")
    public String detail(@RequestParam(value = "id") Long id, Model model) throws IOException, InterruptedException {
        UtilisateurDTO utilisateur = utilisateurService.getUserById(id);
        if (utilisateur == null) {
            logger.info("l'utilisateur n'existe pas");
        }
        logger.info(this.utilisateurService.getUserById(id));
        model.addAttribute("utilisateur", this.utilisateurService.getUserById(id));
        return "detailsUser";
    }

    /**
     * Méthode permet de modifier l'utilisateur en get
     *
     * @param model
     * @param id
     * @return la page "editionUser"
     */
    @RequestMapping(value = "/editionUser", method = RequestMethod.GET)
    public String editionUser(@RequestParam(value = "id") Long id, Model model) throws IOException, InterruptedException {
        model.addAttribute("utilisateur", this.utilisateurService.getUserById(id));
        System.out.println(this.utilisateurService.getUserById(id));
        return "editionUser";

    }

    /**
     * Méthode permet de modifier l'utilisateur en post
     *
     * @param model
     * @param id
     * @param utilisateur
     * @return la page "editionUser"
     */
    @RequestMapping(value = "/editionUser", method = RequestMethod.POST)
    public String editionUser(@RequestParam(value = "id") long id, @ModelAttribute UtilisateurDTO utilisateur, BindingResult errors, Model model) throws IOException, InterruptedException {
        if (errors.hasErrors()) {
            return "editionUser";
        } else {
            UtilisateurDTO utilisateurId = this.utilisateurService.getUserById(id);
            System.out.println(utilisateurId);
            System.out.println(utilisateur.getUsername());
            utilisateurId.setUsername(utilisateur.getUsername());
            utilisateurId.setPrenom(utilisateur.getPrenom());
            utilisateurId.setMail(utilisateur.getMail());
            utilisateurId.setAge(utilisateur.getAge());
            utilisateurId.setPassword(utilisateur.getPassword());
            this.utilisateurService.updateUser(utilisateurId, utilisateur.getId());
            model.addAttribute("utilisateurs", this.utilisateurService.getAllUsers());
            return "redirect:/utilisateur/list";
        }
    }

    private Utilisateur convertToEntity(UtilisateurDTO utilisateurDTO) throws ParseException {
        ModelMapper mapper = new ModelMapper();
        Utilisateur utilisateur = mapper.map(utilisateurDTO, Utilisateur.class);

        return utilisateur;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) throws IOException, InterruptedException {
        model.addAttribute("utilisateurs", this.utilisateurService.getAllUsers());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info(authentication);
        return "home";
    }

}
