package com.example1.library.service;
import com.example1.library.model.dto.LivreDTO;
import com.example1.library.model.dto.UtilisateurDTO;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
/*import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;*/
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

    public void saveUser(UtilisateurDTO utilisateur1) throws IOException,InterruptedException
    {
        String urlCree = "http://localhost:8090/api/utilisateur/add";
        System.out.println(urlCree);
        this.sendPostRequest(utilisateur1,urlCree);
    }

    public void updateUser(UtilisateurDTO utilisateur1, Long id) throws IOException,InterruptedException
    {
        String urlUpdate = "http://localhost:8090/api/utilisateur/update/" +id;
        System.out.println(urlUpdate);
        System.out.println(utilisateur1);
        this.sendPutRequest(utilisateur1,urlUpdate);
    }

    public void connectUser(UtilisateurDTO utilisateur1) throws IOException,InterruptedException
    {
        String urlConnecte = "http://localhost:8090/api/auth/login";
        System.out.println(urlConnecte);
        this.sendPostRequest(utilisateur1,urlConnecte);
    }

    public void sendPostRequest(UtilisateurDTO utilisateur, String url) throws ClientProtocolException, IOException {
       CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        JSONObject json = new JSONObject();
        json.put("id", utilisateur.getId());
        json.put("username", utilisateur.getUsername());
        json.put("prenom", utilisateur.getPrenom());
        json.put("age", utilisateur.getAge());
        json.put("mail", utilisateur.getMail());
        json.put("password", utilisateur.getPassword());
        json.put("statut", utilisateur.getStatut());
        json.toString();
        System.out.println(json);
       StringEntity entity = new StringEntity(json.toString());
        System.out.println(entity);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

       CloseableHttpResponse response = client.execute(httpPost);
       System.out.println(response);
       System.out.println(response.getStatusLine().getStatusCode());
   //     assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
        client.close();
    }

    public void sendPutRequest(UtilisateurDTO utilisateur, String url) throws ClientProtocolException, IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(url);

        JSONObject json = new JSONObject();
        json.put("id", utilisateur.getId());
        json.put("username", utilisateur.getUsername());
        json.put("prenom", utilisateur.getPrenom());
        json.put("age", utilisateur.getAge());
        json.put("mail", utilisateur.getMail());
        json.put("password", utilisateur.getPassword());
        json.put("statut", utilisateur.getStatut());
        json.toString();
        System.out.println(json);
        StringEntity entity = new StringEntity(json.toString());
        System.out.println(entity);
        httpPut.setEntity(entity);
        httpPut.setHeader("Accept", "application/json");
        httpPut.setHeader("Content-type", "application/json");

        CloseableHttpResponse response = client.execute(httpPut);
        System.out.println(response);
        System.out.println(response.getStatusLine().getStatusCode());
        //     assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
        client.close();
    }

}
