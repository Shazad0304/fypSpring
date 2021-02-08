package com.example.crypto.services;

import com.example.crypto.Model.CMC.CoinInfo;
import com.example.crypto.Model.History;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class historyService {

    private final WebClient client;

    public historyService(WebClient.Builder webClientBuilder){
        this.client = webClientBuilder
                .baseUrl("https://poloniex.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public Flux<History> Getdata(String currency){
        return this.client.get()
                .uri(uriBuilder ->
                        uriBuilder.path("/public?command=returnChartData&currencyPair=USDT_"+currency+"&start=1439014500&end=9999999999&period=86400")
                                .build()
                )
                .retrieve()
                .bodyToFlux(History.class);
    }
}
