package com.example.library.service;

import com.example.library.model.dto.LivreDTO;
import com.example.library.model.entity.Livre;
import com.example.library.repository.LivreRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LivreService {

    private LivreDTO mapLivreToLivreDTO(Livre livre) {
        ModelMapper mapper = new ModelMapper();
        LivreDTO livreDTO = mapper.map(livre, LivreDTO.class);
        return livreDTO;
    }

    @Autowired
    LivreRepository livreRepository;


    private static final Logger logger = LogManager.getLogger(LivreService.class);

    /**
     * Méthode permet de lister toutes les Topos via ce service
     *
     * * @return la liste des topos
     */
    public List<LivreDTO> getAllLivres()
    {
        List<Livre> LivreList = livreRepository.findAll();
        logger.debug(LivreList.size());
        HttpClient client = HttpClient.newHttpClient();
        if(LivreList.size() > 0) {
            System.out.println("ici2");
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("http://localhost8080/api/livre/list"))
                    .build();
            System.out.println(request);
            return LivreList.stream()
                    .map(this::mapLivreToLivreDTO)
                    .collect(Collectors.toList());
        } else {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost8080/livre/list"))
                    .build();
            return new ArrayList<LivreDTO>();
        }
    }

    /**
     * Méthode permet de consulter le livre en fonction de l'id via ce service
     *
     * @param id
     * * @return le tivre via id
     */
    public LivreDTO getLivreById(Long id)
    {
        Optional<Livre> livre= this.livreRepository.findById(id);
        if (!livre.isPresent()){
            return null;
        }
        return mapLivreToLivreDTO(livre.get());

    }

    /**
     * Méthode permet de sauvegarder le livre via ce service
     *
     * @param livre
     * * @return le topo sauvegardé
     */
    public LivreDTO saveLivre(Livre livre)
    {

        Livre livreSauve= this.livreRepository.save(livre);
        return mapLivreToLivreDTO(livreSauve);

    }

    /**
     * Méthode permet d'effacer le livre en fonction de l'id via ce service
     *
     * @param id
     */
    public void deleteLivreById(Long id)
    {
        livreRepository.deleteById(id);
    }


}
