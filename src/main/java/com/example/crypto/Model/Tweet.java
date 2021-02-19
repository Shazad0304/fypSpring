package com.example.crypto.Model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Getter
@Setter
public class Tweet {


    @Id
    private String id;

    private String userId;
    private String name;
    private String date;
    private String link;
    private String username;
    private String data;
    private List<String> media;
    private int likes;
    private int comments;
    private int retweets;

}
