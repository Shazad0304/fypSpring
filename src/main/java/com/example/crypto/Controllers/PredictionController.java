package com.example.crypto.Controllers;

import com.example.crypto.ML.LinearRegression;
import com.example.crypto.Model.CoinHistory;
import com.example.crypto.Model.PredictionModel;
import com.example.crypto.services.Polenix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/prediction")
public class PredictionController {

    @Autowired
    private Polenix p;

    @GetMapping("/{coin}/{days}")
    public ResponseEntity<?> get(@PathVariable("coin") String coin,@PathVariable("days") int days){



        List<PredictionModel> response = new ArrayList();

        for(int m = days;m > 0;m--) {
            long DAY_IN_MS = 1000 * 60 * 60 * 24;
            Date date = new Date(System.currentTimeMillis() - (m * DAY_IN_MS));
            long from = date.getTime() / 1000;

            //data collection
            List<CoinHistory> lst = p.getCoinHistory(coin, from).collectList().block();

            double[] x = new double[lst.size()];
            double[] y = new double[lst.size()];
            int i = 0;

            for (CoinHistory ch : lst) {
                x[i] = i + 1;
                y[i] = ch.getClose();
                i++;
            }
                LinearRegression o = new LinearRegression(x, y);

                PredictionModel rsp = new PredictionModel();
                Date tstamp = new Date(System.currentTimeMillis() + ((m) * DAY_IN_MS));
                rsp.setTimestamp(tstamp.getTime() / 1000);
                rsp.setPrediction(o.predict(i));
                response.add(rsp);
                i++;

        }

        Collections.sort(response, (s1, s2) -> {
            return s1.getTimestamp() < s2.getTimestamp() ? -1
                : s1.getTimestamp() > s2.getTimestamp() ? 1
                : 0;
        });

        return ResponseEntity.status(200).body(response);
    }
}
