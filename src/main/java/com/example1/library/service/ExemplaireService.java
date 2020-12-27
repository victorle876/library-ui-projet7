package com.example1.library.service;

import com.example1.library.model.dto.ExemplaireDTO;
import com.example1.library.model.dto.LivreDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

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
        System.out.println(exemplaires);
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

    public void saveExemplaire(ExemplaireDTO exemplaireCree , Long id) throws IOException,InterruptedException
    {
        String urlCree = "http://localhost:8090/api/livre/addExemplaire/" + id;
        this.sendPostRequest(exemplaireCree,urlCree);
    }

    public void updateExemplaire(ExemplaireDTO exemplaireUpdated, Long id) throws IOException,InterruptedException
    {
        String urlUpdate = "http://localhost:8090/api/exemplaire/update/" +id;
        this.sendPutRequest(exemplaireUpdated,urlUpdate);
    }

    public void sendPostRequest(ExemplaireDTO exemplaire, String url) throws ClientProtocolException, IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        JSONObject json = new JSONObject();
        json.put("id", exemplaire.getId());
        json.put("collection", exemplaire.getCollection());
        json.put("editeur", exemplaire.getEditeur());
        json.put("description", exemplaire.getDescription());
        json.put("isbn", exemplaire.getIsbn());
        json.put("createdAt", exemplaire.getCreatedAt());
         ObjectMapper objectMapper = new ObjectMapper();
        String livreAsString = objectMapper.writeValueAsString(exemplaire.getLivre());
        System.out.println("livreAsString:" + new JSONObject(livreAsString));
        json.put("livre", new JSONObject(livreAsString));
        System.out.println("json livre: " + json);
        StringEntity entity = new StringEntity(json.toString());
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        CloseableHttpResponse response = client.execute(httpPost);
        System.out.println(response);
   //     assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
        client.close();
    }

    public void sendPutRequest(ExemplaireDTO exemplaire, String url) throws ClientProtocolException, IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(url);

        JSONObject json = new JSONObject();
        json.put("id", exemplaire.getId());
        json.put("collection", exemplaire.getCollection());
        json.put("editeur", exemplaire.getEditeur());
        json.put("description", exemplaire.getDescription());
        json.put("isbn", exemplaire.getIsbn());
        json.put("nombre", exemplaire.getNombre());
       ObjectMapper objectMapper = new ObjectMapper();
        String livreAsString = objectMapper.writeValueAsString(exemplaire.getLivre());
        System.out.println("livreAsString:" + new JSONObject(livreAsString));
        json.put("livre", new JSONObject(livreAsString));
        System.out.println(json);
        StringEntity entity = new StringEntity(json.toString());
        httpPut.setEntity(entity);
        httpPut.setHeader("Accept", "application/json");
        httpPut.setHeader("Content-type", "application/json");
        CloseableHttpResponse response = client.execute(httpPut);
        System.out.println(response);
        //     assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
        client.close();
    }

}
