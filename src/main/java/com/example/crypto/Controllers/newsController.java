package com.example.crypto.Controllers;

import com.example.crypto.Model.CMC.CoinInfo;
import com.example.crypto.Model.News;
import com.example.crypto.Model.NewsList;
import com.example.crypto.News.newsService;
import com.example.crypto.services.cmcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/news")
public class newsController {

    @Autowired
    private newsService ns;


    @GetMapping("get")
    public ResponseEntity<?> getNews(
            @RequestParam(value = "search",required = false) String search,
            @RequestParam(value = "startStamp",required = false) Long start,
            @RequestParam(value = "endStamp",required = false) Long end
    ){

        List<NewsList> nl = ns.getDetails().block().getData();

        if(search != null && !search.isEmpty()){

            nl = nl.stream().filter(x -> x.getTitle().toLowerCase().indexOf(search.toLowerCase()) > -1
                    || x.getBody().toLowerCase().indexOf(search.toLowerCase()) > -1
        || x.getTags().toLowerCase().indexOf(search.toLowerCase()) > -1
                    || x.getCategories().toLowerCase().indexOf(search.toLowerCase()) > -1).collect(Collectors.toList());

        }

        if(start != null && start != 0 && end != null && end != 0){

            nl = nl.stream().filter(x -> x.getPublished_on() >= start && x.getPublished_on() <= end).collect(Collectors.toList());

        }


        return ResponseEntity.status(200).body(nl);
    }
}
