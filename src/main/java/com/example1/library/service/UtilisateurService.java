package com.example1.library.service;
import com.example1.library.model.dto.UtilisateurDTO;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import org.apache.http.impl.client.HttpClients;
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
        this.sendPostRequest(utilisateur1,urlUpdate);
    }

    public void sendPostRequest(UtilisateurDTO utilisateur, String url) throws ClientProtocolException, IOException {
       CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        String json = "{" + "id:"+ utilisateur.getId() + ","+ "username:"+ utilisateur.getUsername() +"," + "prenom:"
                + utilisateur.getPrenom() +","+"mail:"+ utilisateur.getMail() + ","  + "password:" + utilisateur.getPassword()+  ","+
                "age:"+ utilisateur.getAge() + "," +
                "statut:"+ utilisateur.getStatut() +
                 "}";
        System.out.println(json);
        StringEntity entity = new StringEntity(json);
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

}
