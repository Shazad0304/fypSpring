package com.example.crypto.services;

import com.example.crypto.Model.Tweet;
import com.example.crypto.mongorepo.connection;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class TweetService {

    private MongoOperations mo = connection.get();

    public Collection<Tweet> saveAll(List<Tweet> tweets){
        return mo.insertAll(tweets);
    }

    public List<Tweet> getData(int page){
        List<Tweet> response = mo.findAll(Tweet.class);
        int size = (page*10)+10;
        return size > response.size() ? new ArrayList<>() : response.subList(page*10,size);
    }
}
