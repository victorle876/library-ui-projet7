package com.example1.library.service;

import com.example1.library.model.dto.LivreDTO;
import com.example1.library.model.dto.UtilisateurDTO;
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
  //      System.out.println(livres);
        return livres;

    }

    public LivreDTO getLivreById(Long id) throws IOException,InterruptedException
    {
        LivreDTO livreId= httpService.sendGetRequest("http://localhost:8090/api/livre/detail/" + id, LivreDTO.class);
        System.out.println(livreId);
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
        this.sendPostRequest(livreUpdated,urlUpdate);
    }

    public void sendPostRequest(LivreDTO livre, String url) throws ClientProtocolException, IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        String json = "{" + "id:"+ livre.getId() + ","+ "titre:"+ livre.getTitre() +"auteur:"
                + livre.getAuteur() + "," + "categorie:"+ livre.getCategorie()+
                "}";
        StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        CloseableHttpResponse response = client.execute(httpPost);
   //     assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
        client.close();
    }

}
