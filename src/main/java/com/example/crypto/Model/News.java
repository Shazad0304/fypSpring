package com.example.crypto.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class News {

    @JsonProperty(value = "Data")
    private List<NewsList> Data;

}

