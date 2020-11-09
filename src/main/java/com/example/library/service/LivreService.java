package com.example.library.service;

import com.example.library.model.dto.LivreDTO;
import com.example.library.model.dto.UtilisateurDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
public class LivreService {

    private static final Logger logger = LogManager.getLogger(LivreService.class);

    HttpService httpService = new HttpService();

    /**
     * Méthode permet de lister toutes les utilisateurs via ce service
     * <p>
     * * @return la liste des utilisateurs
     */
    public List<LivreDTO> getAllLivres() throws IOException,InterruptedException {
        List<LivreDTO> livres = httpService.sendGetListRequest("http://localhost8080/api/livre/list", LivreDTO.class);
        return livres;

    }

}
