package com.example.crypto.Controllers;

import com.example.crypto.Model.Tweet;
import com.example.crypto.services.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<?> getAll(@RequestParam(name = "page",required = false,defaultValue = "0") int page){
        return ResponseEntity.status(200).body(ts.getData(page));
    }
}
