package com.example.library.service;
import com.example.library.model.dto.PretDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
public class PretService {
    private static final Logger logger = LogManager.getLogger(PretService.class);

    HttpService httpService = new HttpService();

    /**
     * Méthode permet de lister toutes les utilisateurs via ce service
     * <p>
     * * @return la liste des utilisateurs
     */
    public List<PretDTO> getAllPrets() throws IOException,InterruptedException {
        List<PretDTO> prets = httpService.sendGetListRequest("http://localhost:8090/api/pret/list", PretDTO.class);
  //      System.out.println(prets);
        return prets;

    }

}
