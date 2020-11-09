package com.example.library.service;
import com.example.library.model.dto.UtilisateurDTO;

import com.example.library.model.entity.Utilisateur;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
public class UtilisateurService {
    private static final Logger logger = LogManager.getLogger(UtilisateurService.class);

    HttpService httpService = new HttpService();

    /**
     * Méthode permet de lister toutes les utilisateurs via ce service
     * <p>
     * * @return la liste des utilisateurs
     */
    public List<UtilisateurDTO> getAllUsers() throws IOException,InterruptedException {
        List<UtilisateurDTO> utilisateurs = httpService.sendGetListRequest("http://localhost8080/api/utilisateur/list", UtilisateurDTO.class);
        return utilisateurs;

    }

}
