package com.example.library.service;

import com.example.library.model.dto.ExemplaireDTO;
import com.example.library.model.entity.Exemplaire;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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

}
