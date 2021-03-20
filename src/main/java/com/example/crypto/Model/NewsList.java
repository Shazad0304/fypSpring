package com.example.crypto.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewsList {

    private String id;
    private String guid;
    private Long published_on;
    private String imageurl;
    private String title;
    private String url;
    private String source;
    private String body;
    private String tags;
    private String categories;
    private String upvotes;
    private String downvotes;
    private String lang;
}
