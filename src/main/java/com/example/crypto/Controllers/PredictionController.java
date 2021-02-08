package com.example.crypto.Controllers;

import com.example.crypto.ML.LinearRegression;
import com.example.crypto.ML.NeuralNetwork;
import com.example.crypto.ML.PolynomialRegression;
import com.example.crypto.Model.History;
import com.example.crypto.services.historyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/prediction")
public class PredictionController {

    @Autowired
    private historyService hs;

    @GetMapping
    public ResponseEntity<?> getData(@RequestParam(value="days",required = false,defaultValue = "0") int duration,
                                     @RequestParam(value = "currency",required = true) String currency){

        List<History> oh = hs.Getdata(currency).collectList().block();
        int count = (int) oh.stream().count();

        List<Double> x = new ArrayList<>();
        List<Double> y = new ArrayList<>();
        double prvValue = 0;
        int i = 0;

        for(History h : oh){

                x.add((double) i);
                y.add(h.getHigh());
                i++;

        }

        double[] xarr = x.stream().mapToDouble(d -> d).toArray();
        double[] yarr = y.stream().mapToDouble(d -> d).toArray();
        LinearRegression ln = new LinearRegression(xarr,yarr);

        double[][] u = { {1,10}, {2,20}, {3,40}, {4,80}, {5,160}, {6,200} };
        double[][] v = { {100}, {350}, {1500}, {6700}, {20160}, {40000} };
        PolynomialRegression regression = new PolynomialRegression(xarr, yarr, 4);
        NeuralNetwork n = new NeuralNetwork(2,6,1);
        n.fit(u,v,500000);


        return ResponseEntity.status(200).body(n.predict(new double[] {7,300}));
    }
}
