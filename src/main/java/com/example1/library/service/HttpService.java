package com.example1.library.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class HttpService {
    private final HttpClient httpClient= HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

    public HttpService() {}

    public <T> T sendGetRequest(String url, Class<T> responseType) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).header("Accept", "application/json").build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(response);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        System.out.println(response.body());
        System.out.println(responseType);
        return objectMapper.readValue(response.body(), responseType);
    }

    public <T> List<T> sendGetListRequest(String url, Class<T> responseType) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).header("Accept", "application/json").build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.body(), objectMapper.getTypeFactory().constructCollectionType(List.class, responseType));
    }

}
