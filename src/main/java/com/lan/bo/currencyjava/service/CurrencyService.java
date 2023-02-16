package com.lan.bo.currencyjava.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

 
@Service
public class CurrencyService {
    @Autowired
    private RestTemplate template;

    public ResponseEntity<String> responseByApi(String from, String to, BigDecimal amount){
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.set("apikey", "jqPB058UIYBeQoPFJNMfd73RtwJEjFcK");
        
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        String urlTemplate = UriComponentsBuilder.fromHttpUrl("https://api.apilayer.com/exchangerates_data/convert")
        .queryParam("to", "{to}")
        .queryParam("from", "{from}")
        .queryParam("amount", "{amount}")
        .encode()
        .toUriString();

        Map<String, String> params = new HashMap<>();
        params.put("to", to);
        params.put("from", from);
        params.put("amount", amount.toString()); 
        try{
            return template.exchange( urlTemplate, HttpMethod.GET, requestEntity , String.class, params);
        }catch(HttpClientErrorException e){
            return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }
    }
}
