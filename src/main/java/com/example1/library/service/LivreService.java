package com.example1.library.service;

import com.example1.library.model.dto.LivreDTO;
import com.example1.library.model.dto.UtilisateurDTO;
import com.example1.library.model.entity.Livre;
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
  //      logger.info(livres);
        return livres;

    }

    public List<LivreDTO> findLivreByAuteurOrTitre(String recherche,String recherche1) throws IOException,InterruptedException {
        List<LivreDTO> livres = httpService.sendGetListRequest("http://localhost:8090/api/livre/searchLivreByAuteurOrTitre", LivreDTO.class);
        //      logger.info(livres);
        return livres;

    }

    public LivreDTO getLivreById(Long id) throws IOException,InterruptedException
    {
        LivreDTO livreId= httpService.sendGetRequest("http://localhost:8090/api/livre/detail/" + id, LivreDTO.class);
        logger.info("livreId: " + livreId);
        return livreId;
    }

    public void saveLivre(LivreDTO livreCree) throws IOException,InterruptedException
    {
        String urlCree = "http://localhost:8090/api/livre/add";
        this.sendPostRequest(livreCree,urlCree);
    }

    public void updateLivre(LivreDTO livreUpdated, Long id) throws IOException,InterruptedException
    {
        String urlUpdate = "http://localhost:8090/api/livre/update/" +id;
        this.sendPutRequest(livreUpdated,urlUpdate);
    }

    public void sendPostRequest(LivreDTO livre, String url) throws ClientProtocolException, IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        JSONObject json = new JSONObject();
        json.put("id", livre.getId());
        json.put("titre", livre.getTitre());
        json.put("auteur", livre.getAuteur());
        json.put("categorie", livre.getCategorie());
        json.toString();
        StringEntity entity = new StringEntity(json.toString());
        httpPost.setEntity(entity);
        logger.info("livre: " + entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        CloseableHttpResponse response = client.execute(httpPost);
     //   assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
        client.close();
    }

    public void sendPutRequest(LivreDTO livre, String url) throws ClientProtocolException, IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(url);

        JSONObject json = new JSONObject();
        json.put("id", livre.getId());
        json.put("titre", livre.getTitre());
        json.put("auteur", livre.getAuteur());
        json.put("categorie", livre.getCategorie());
        json.toString();
        logger.info("json livre:" + json);
        StringEntity entity = new StringEntity(json.toString());
        logger.info(entity);
        httpPut.setEntity(entity);
        httpPut.setHeader("Accept", "application/json");
        httpPut.setHeader("Content-type", "application/json");

        CloseableHttpResponse response = client.execute(httpPut);
        logger.info("response put request livre: " +response);
        logger.info("status code: " + response.getStatusLine().getStatusCode());
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
        client.close();
    }

}
