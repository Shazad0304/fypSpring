package com.example.crypto.News;

import com.example.crypto.Model.CMC.CoinInfo;
import com.example.crypto.Model.News;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class newsService {

    @Value("${news.apikey}")
    private String apiKey;

    private final WebClient client;

    public newsService(WebClient.Builder webClientBuilder){
        this.client = webClientBuilder
                .baseUrl("https://min-api.cryptocompare.com/data/v2")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public Mono<News> getDetails(){
        return this.client.get()
                .uri(uriBuilder ->
                        uriBuilder.path("/news/")
                                .queryParam("lang","EN")
                                .build()
                )
                .header("authorization","Apikey "+apiKey)
                .retrieve()
                .bodyToMono(News.class);
    }
}
