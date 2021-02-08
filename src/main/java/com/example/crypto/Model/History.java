package com.example.crypto.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class History {
    private Long date;
    private double high;
    private double low;
    private double open;
    private double close;
    private double volume;
    private double quoteVolume;
    private double weightedAverage;
}
