package com.example.library.service;

import com.example.library.model.dto.UtilisateurDTO;
import com.example.library.model.entity.Utilisateur;
import com.example.library.repository.UtilisateurRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.net.Authenticator;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UtilisateurService {

    private UtilisateurDTO mapUtilisateurToUtilisateurDTO(Utilisateur utilisateur) {
        ModelMapper mapper = new ModelMapper();
        UtilisateurDTO utilisateurDTO = mapper.map(utilisateur, UtilisateurDTO.class);
        return utilisateurDTO;
    }

    @Autowired
    UtilisateurRepository utilisateurRepository;

    /**
     * Méthode permet de lister tous les utilisateurs via ce service
     *
     * * @return la liste des utilisateurs
     */
    public List<UtilisateurDTO> getAllUsers()
    {
        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        HttpClient client = HttpClient.newHttpClient();
        if(utilisateurList.size() > 0) {
            System.out.println("ici");
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("http://localhost8080/api/utilisateur/list"))
                    .build();
            return utilisateurList.stream()
                    .map(this::mapUtilisateurToUtilisateurDTO)
                    .collect(Collectors.toList());
        } else {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost8080/utilisateur/list"))
                    .build();
            return new ArrayList<UtilisateurDTO>();
        }
    }

    /**
     * Méthode permet de consulter l'utilisateur en fonction de l'id via ce service
     *
     * @param id
     * * @return l'utilisateur via id
     */
    public UtilisateurDTO getUserById(Long id)
    {
        Optional<Utilisateur> utilisateur= this.utilisateurRepository.findById(id);
        if (!utilisateur.isPresent()){
            return null;
        }
        return mapUtilisateurToUtilisateurDTO(utilisateur.get());
    }

    public UtilisateurDTO getUserByUsername(String username)
    {
        Optional<Utilisateur> utilisateur= this.utilisateurRepository.findByUsername(username);
        System.out.println(utilisateur);
        if (!utilisateur.isPresent()){
            return null;
        }
        return mapUtilisateurToUtilisateurDTO(utilisateur.get());
    }

    public UtilisateurDTO getUserByUsernameAndPassword(String username, String password)
    {
        Optional<Utilisateur> utilisateur= this.utilisateurRepository.findByUsernameAndPassword(username,password);
        System.out.println(utilisateur);
        if (!utilisateur.isPresent()){
            return null;
        }
        return mapUtilisateurToUtilisateurDTO(utilisateur.get());
    }

    /**
     * Méthode permet de sauvegarder l'utilisateur via ce service
     *
     * @param utilisateur
     * * @return la voie sauvegardée
     */
    public UtilisateurDTO saveUser(Utilisateur utilisateur)
    {
        Utilisateur utilisateurSauve= this.utilisateurRepository.save(utilisateur);
        return mapUtilisateurToUtilisateurDTO(utilisateurSauve);
    }

    /**
     * Méthode permet d'effacer l'utilisateur en fonction de l'id via ce service
     *
     * @param id
     */
    public void deleteUserById(Long id)
    {
        utilisateurRepository.deleteById(id);
    }

    /**
     * Méthode permet de vérifier l'existence lors de la connexion via ce service
     *
     * @return l'utilisateur
     */
    public UtilisateurDTO getUtilisateurConnected (){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailUtilisateur = authentication.getName();
        Utilisateur utilisateurConnecte = this.utilisateurRepository.findByMail(emailUtilisateur).get();
        return mapUtilisateurToUtilisateurDTO(utilisateurConnecte);
    }

}
