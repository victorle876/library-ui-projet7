package com.example1.library.service;

import com.example1.library.model.dto.ExemplaireDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ExemplaireService {

    private static final Logger logger = LogManager.getLogger(ExemplaireService.class);

    HttpService httpService = new HttpService();

    /**
     * Méthode permet de lister toutes les Topos via ce service
     * <p>
     * * @return la liste des topos
     */
    public List<ExemplaireDTO> getAllExemplaires() throws IOException,InterruptedException {
        List<ExemplaireDTO> exemplaires = httpService.sendGetListRequest("http://localhost:8090/api/exemplaire/list", ExemplaireDTO.class);
  //      System.out.println(exemplaires);
        return exemplaires;

    }

    public List<ExemplaireDTO> findExemplairesByLivre(Long id) throws IOException,InterruptedException {
        String url1 = "http://localhost:8090/api/livre/listExemplaireByLivre/"+ id;
        List<ExemplaireDTO> exemplaires = httpService.sendGetListRequest(url1, ExemplaireDTO.class);
        System.out.println(exemplaires);
        return exemplaires;

    }

    /**
     * Méthode permet de consulter l'utilisateur en fonction de l'id via ce service
     *
     * @param id
     * * @return l'utilisateur via id
     */
    public ExemplaireDTO getExemplaireById(Long id) throws IOException,InterruptedException
    {
        ExemplaireDTO exemplaireId= httpService.sendGetRequest("http://localhost:8090/api/exemplaire/detail/" + id, ExemplaireDTO.class);
        System.out.println(exemplaireId);
        return exemplaireId;
    }

    public ExemplaireDTO saveExemplaire(ExemplaireDTO exemplaire1) throws IOException,InterruptedException
    {
        ExemplaireDTO exemplaireSauve= httpService.sendGetRequest("http://localhost:8090/api/exemplaire/save", ExemplaireDTO.class);
        System.out.println(exemplaireSauve);
        return exemplaireSauve;
    }

}
