package com.example.crypto.Controllers;

import com.example.crypto.Model.Tweet;
import com.example.crypto.services.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sentiment")
public class TweetController {

    @Autowired
    private TweetService ts;

    @PostMapping("/batch")
    public ResponseEntity<?> addAll(@RequestBody List<Tweet> tweets){
        return ResponseEntity.status(200).body(ts.saveAll(tweets));
    }

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(name = "page",required = false,defaultValue = "0") int page,
                                    @RequestParam(value = "search",required = false) String search,
                                    @RequestParam(value = "startStamp",required = false) Long start,
                                    @RequestParam(value = "endStamp",required = false) Long end
    ){
        List<Tweet> tweets = ts.getData(page);

        if(search != null && !search.isEmpty()){

            tweets = tweets.stream().filter(x -> x.getUsername().toLowerCase().indexOf(search.toLowerCase()) > -1
                    || x.getData().toLowerCase().indexOf(search.toLowerCase()) > -1).collect(Collectors.toList());

        }

        if(start != null && start != 0 && end != null && end != 0){

            tweets = tweets.stream().filter(x -> Math.round(Double.parseDouble(x.getDate())) >= start &&
                    Math.round(Double.parseDouble(x.getDate())) <= end).collect(Collectors.toList());

        }

        return ResponseEntity.status(200).body(tweets);
    }
}
