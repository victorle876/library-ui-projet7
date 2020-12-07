package com.example1.library.service;
import com.example1.library.model.dto.ExemplaireDTO;
import com.example1.library.model.dto.PretDTO;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

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

    public PretDTO getExemplaireById(Long id) throws IOException,InterruptedException
    {
        PretDTO exemplaireId= httpService.sendGetRequest("http://localhost:8090/api/pret/detail" + id, PretDTO.class);
        System.out.println(exemplaireId);
        return exemplaireId;
    }

    public void savePret(PretDTO pretCree) throws IOException,InterruptedException
    {
        String urlCree = "http://localhost:8090/api/pret/add";
        this.sendPostRequest(pretCree,urlCree);
    }

    public void updatePret(PretDTO pretUpdated, Long id) throws IOException,InterruptedException
    {
        String urlUpdate = "http://localhost:8090/api/pret/update" +id;
        this.sendPostRequest(pretUpdated,urlUpdate);
    }

    public void sendPostRequest(PretDTO pret, String url) throws ClientProtocolException, IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        String json = "{" + "id:"+ pret.getId() + ","+ "emprunte :"+ pret.getEmprunte()
                + pret.getEmprunte() + "," + "retourne:"+ pret.getRetourne()+
                "," + "prolonge:"+ pret.getProlonge()+
                "}";
        StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        CloseableHttpResponse response = client.execute(httpPost);
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
        client.close();
    }

}
