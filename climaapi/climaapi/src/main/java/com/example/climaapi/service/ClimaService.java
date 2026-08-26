package com.example.climaapi.service;

import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Service
public class ClimaService {

 private String consultarURL(String apiUrl){
        String dados = "";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            dados = responseEntity.getBody();
        } else {
            dados = "Falha ao obter dados. Código de status: " + responseEntity.getStatusCode();
        }
        return dados;
    }

    public String buscarClimaBH(){
        return consultarURL("https://api.open-meteo.com/v1/forecast?latitude=-19.9167&longitude=-43.9345&current=temperature_2m");
    }
    
    public String buscarClima(String cidade){
        return consultarURL("https://geocoding-api.open-meteo.com/v1/search?name=" + cidade + "&count=1&language=pt&format=json");
    }
}