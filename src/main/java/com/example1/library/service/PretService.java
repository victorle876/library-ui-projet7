package com.example1.library.service;
import com.example1.library.model.dto.ExemplaireDTO;
import com.example1.library.model.dto.PretDTO;
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

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;


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

    public List<PretDTO> getAllPretsDispo() throws IOException,InterruptedException {
        List<PretDTO> pretsDispo = httpService.sendGetListRequest("http://localhost:8090/api/pret/listPretDispo", PretDTO.class);
        //      System.out.println(prets);
        return pretsDispo;

    }

    public List<PretDTO> getAllPretsRetourneOrProlonge() throws IOException,InterruptedException {
        List<PretDTO> pretsRetourneOrProlonge = httpService.sendGetListRequest("http://localhost:8090/api/pret/listPretRetourneOrProlonge", PretDTO.class);
        //      System.out.println(prets);
        return pretsRetourneOrProlonge;
    }

    public PretDTO getPretById(Long id) throws IOException,InterruptedException
    {
        PretDTO pretId= httpService.sendGetRequest("http://localhost:8090/api/pret/detail/" + id, PretDTO.class);
        System.out.println(pretId);
        return pretId;
    }

    public void savePret(PretDTO pretCree, Long id) throws IOException,InterruptedException
    {
        String urlCree = "http://localhost:8090/api/exemplaire/addPretDispo/" + id;
        this.sendPostRequest(pretCree,urlCree);
    }

    public void updatePret(PretDTO pretUpdated, Long id) throws IOException,InterruptedException
    {
        String urlUpdate = "http://localhost:8090/api/pret/update/" +id;
        this.sendPutRequest(pretUpdated,urlUpdate);
    }

    public void savePretProlonge(PretDTO pretProlonge, Long id) throws IOException,InterruptedException
    {
        String urlUpdate = "http://localhost:8090/api/pret/makePretProlonge/" +id;
        this.sendPutRequest(pretProlonge,urlUpdate);
    }

    public void savePretEmprunte(PretDTO pretEmprunte, Long id) throws IOException,InterruptedException
    {
        String urlUpdate = "http://localhost:8090/api/pret/makePretEmprunte/" +id;
        this.sendPutRequest(pretEmprunte,urlUpdate);
    }

    public void savePretRetourne(PretDTO pretRetourne, Long id) throws IOException,InterruptedException
    {
        String urlUpdate = "http://localhost:8090/api/pret/makePretRetourne/" +id;
        this.sendPutRequest(pretRetourne,urlUpdate);
    }

    public void sendPostRequest(PretDTO pret, String url) throws ClientProtocolException, IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        JSONObject json = new JSONObject();
        json.put("id", pret.getId());
        json.put("emprunte", pret.getEmprunte());
        json.put("retourne", pret.getRetourne());
        json.put("prolonge", pret.getProlonge());
        json.put("disponible", pret.getDisponible());
        json.put("nombreprolonge", pret.getNombreProlonge());
        StringEntity entity = new StringEntity(json.toString());
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        System.out.println(json);
        CloseableHttpResponse response = client.execute(httpPost);
         assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
        client.close();
    }

    public void sendPutRequest(PretDTO pret, String url) throws ClientProtocolException, IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(url);

        JSONObject json = new JSONObject();
        json.put("id", pret.getId());
        json.put("emprunte", pret.getEmprunte());
        json.put("prolonge", pret.getProlonge());
        json.put("retourne", pret.getRetourne());
        json.put("disponible", pret.getDisponible());
        json.put("nombreprolonge", pret.getNombreProlonge());
        System.out.println("lecture:" + pret.getExemplaire());
        System.out.println("lecture2:" + pret.getExemplaire().getCreatedAt());
        ObjectMapper objectMapper = new ObjectMapper();
        String exemplaireAsString = objectMapper.writeValueAsString(pret.getExemplaire());
        System.out.println("exemplaireAsString:" + new JSONObject(exemplaireAsString));
        json.put("exemplaire", new JSONObject(exemplaireAsString));
        System.out.println("json: " + json);
        StringEntity entity = new StringEntity(json.toString());
        httpPut.setEntity(entity);
        httpPut.setHeader("Accept", "application/json");
        httpPut.setHeader("Content-type", "application/json");

        CloseableHttpResponse response = client.execute(httpPut);
         assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
        client.close();
    }

}
