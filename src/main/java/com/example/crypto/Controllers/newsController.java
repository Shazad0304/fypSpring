package com.example.crypto.Controllers;

import com.example.crypto.Model.CMC.CoinInfo;
import com.example.crypto.News.newsService;
import com.example.crypto.services.cmcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/news")
public class newsController {

    @Autowired
    private newsService ns;


    @GetMapping("get")
    public ResponseEntity<?> getNews(){

        return ResponseEntity.status(200).body(ns.getDetails().block().get("Data"));
    }
}
