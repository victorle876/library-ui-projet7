package com.example.library.service;

import com.example.library.model.dto.ExemplaireDTO;
import com.example.library.model.entity.Exemplaire;
import com.example.library.repository.ExemplaireRepository;
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
public class ExemplaireService {

    private ExemplaireDTO mapExemplaireToExemplaireDTO(Exemplaire exemplaire) {
        ModelMapper mapper = new ModelMapper();
        ExemplaireDTO exemplaireDTO = mapper.map(exemplaire, ExemplaireDTO.class);
        return exemplaireDTO;
    }

    @Autowired
    ExemplaireRepository exemplaireRepository;

    private static final Logger logger = LogManager.getLogger(ExemplaireService.class);

    /**
     * Méthode permet de lister toutes les Topos via ce service
     *
     * * @return la liste des topos
     */
    public List<ExemplaireDTO> getAllExemplaires()
    {
        List<Exemplaire> exemplaireList = exemplaireRepository.findAll();
        logger.debug(exemplaireList.size());
        HttpClient client = HttpClient.newHttpClient();
        if(exemplaireList.size() > 0) {
                HttpRequest request = HttpRequest.newBuilder()
                        .GET()
                        .uri(URI.create("http://localhost8080/api/exemplaire/list"))
                        .build();
            return exemplaireList.stream()
                    .map(this::mapExemplaireToExemplaireDTO)
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<ExemplaireDTO>();
        }
    }

    /**
     * Méthode permet de consulter le Stock en fonction de l'id via ce service
     *
     * @param id
     * * @return le stock via id
     */
    public ExemplaireDTO getExemplaireById(Long id)
    {
        Optional<Exemplaire> exemplaire= this.exemplaireRepository.findById(id);
        if (!exemplaire.isPresent()){
            return null;
        }
        return mapExemplaireToExemplaireDTO(exemplaire.get());

    }

    /**
     * Méthode permet de sauvegarder le stock via ce service
     *
     * @param exemplaire
     * * @return le stock sauvegardé
     */
    public ExemplaireDTO saveExemplaire(Exemplaire exemplaire)
    {
        Exemplaire exemplaireSauve= this.exemplaireRepository.save(exemplaire);
        return mapExemplaireToExemplaireDTO(exemplaireSauve);

    }

    /**
     * Méthode permet d'effacer le stock en fonction de l'id via ce service
     *
     * @param id
     */
    public void deleteExemplaireById(Long id)
    {
        exemplaireRepository.deleteById(id);
    }

    public List<Exemplaire> findSiteByTitre(String recherche1) {
        List<Exemplaire>  exemplaireTrouveByTitre = this.exemplaireRepository.findByLivre(recherche1);
        if (exemplaireTrouveByTitre == null){
            throw new RuntimeException("Titre introuvable");
        }
        return exemplaireTrouveByTitre;
    }

    public List<Exemplaire> findSiteByAuteur(String recherche1) {
        List<Exemplaire>  exemplaireTrouveByAuteur = this.exemplaireRepository.findByLivre2(recherche1);
        if (exemplaireTrouveByAuteur == null){
            throw new RuntimeException("Auteur introuvable");
        }
        return exemplaireTrouveByAuteur;
    }

}
