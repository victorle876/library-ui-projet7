package com.example1.library.service;
import com.example1.library.model.dto.UtilisateurDTO;

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
        List<UtilisateurDTO> utilisateurs = httpService.sendGetListRequest("http://localhost:8090/api/utilisateur/list", UtilisateurDTO.class);
        System.out.println(utilisateurs);
        return utilisateurs;

    }

    /**
     * Méthode permet de consulter l'utilisateur en fonction de l'id via ce service
     *
     * @param id
     * * @return l'utilisateur via id
     */
    public UtilisateurDTO getUserById(Long id) throws IOException,InterruptedException
    {
        UtilisateurDTO utilisateurId= httpService.sendGetRequest("http://localhost:8090/api/utilisateur/detail/" + id, UtilisateurDTO.class);
        System.out.println(utilisateurId);
        return utilisateurId;
    }

    public UtilisateurDTO saveUser(UtilisateurDTO utilisateur1) throws IOException,InterruptedException
    {
        UtilisateurDTO utilisateurSauve= httpService.sendGetRequest("http://localhost:8090/api/utilisateur/add", UtilisateurDTO.class);
        System.out.println(utilisateurSauve);
        return utilisateurSauve;
    }

    public UtilisateurDTO updateUser(UtilisateurDTO utilisateur1, Long id) throws IOException,InterruptedException
    {
        UtilisateurDTO utilisateurSauve= httpService.sendGetRequest("http://localhost:8090/api/utilisateur/update/" + id, UtilisateurDTO.class);
        System.out.println(utilisateurSauve);
        return utilisateurSauve;
    }

}
