package com.example1.library.service;

import com.example1.library.model.dto.LivreDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
        List<LivreDTO> livres = httpService.sendGetListRequest("http://localhost:8090/api/livre/list", LivreDTO.class);
  //      System.out.println(livres);
        return livres;

    }

    public LivreDTO getLivreById(Long id) throws IOException,InterruptedException
    {
        LivreDTO livreId= httpService.sendGetRequest("http://localhost:8090/api/livre/detail/" + id, LivreDTO.class);
        System.out.println(livreId);
        return livreId;
    }

    public LivreDTO saveLivre(LivreDTO livre) throws IOException,InterruptedException
    {
        LivreDTO livreSauve= httpService.sendGetRequest("http://localhost:8090/api/livre/save", LivreDTO.class);
        System.out.println(livreSauve);
        return livreSauve;
    }

}
