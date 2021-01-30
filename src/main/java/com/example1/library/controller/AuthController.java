package com.example1.library.controller;

import com.example1.library.model.dto.UtilisateurDTO;
import com.example1.library.service.UtilisateurService;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UtilisateurService utilisateurService;

    @RequestMapping(value = "/connect", method = RequestMethod.GET)
    public String connect(Model model) {
        model.addAttribute("utilisateur", new UtilisateurDTO());
        return "connexion";

    }


    @RequestMapping(value = "/connect", method = RequestMethod.POST)
    public String connect(@ModelAttribute UtilisateurDTO utilisateur, Model model, BindingResult result) throws IOException, InterruptedException {
        if (result.hasErrors()) {
            return "connexion";
        } else {
            System.out.println("login utilisateur: " + utilisateur.getMail());
            System.out.println("mdp utilisateur:" + utilisateur.getPassword());
            String urlConnecte = "http://localhost:8090/api/auth/login";
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(urlConnecte);
            JSONObject json = new JSONObject();
            json.put("mail", utilisateur.getMail());
            json.put("password", utilisateur.getPassword());
            json.toString();
//            this.utilisateurService.connectUser(utilisateur);
            StringEntity entity = new StringEntity(json.toString());
            System.out.println("json: " + json);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            CloseableHttpResponse response = client.execute(httpPost);
            client.close();
            System.out.println("httpPost: " + httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println("statusCode: " + statusCode);
            if (statusCode < 403) {
                model.addAttribute("utilisateurs", this.utilisateurService.getAllUsers());
                return "redirect:/utilisateur/list";
            }
            return "connexion";
        }
    }
}
