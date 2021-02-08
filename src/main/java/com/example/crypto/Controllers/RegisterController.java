package com.example.crypto.Controllers;


import com.example.crypto.ML.LinearRegression;
import com.example.crypto.ML.LinearRegressionClassifier;
import com.example.crypto.Model.ErrorModel;
import com.example.crypto.Model.UserModel;
import com.example.crypto.mongorepo.connection;
import com.example.crypto.services.emailService;
import com.example.crypto.services.userService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/register")
public class RegisterController {


    private final userService us;

    @Autowired
    private emailService es;

    @Autowired
    public RegisterController(@Qualifier("userService") userService r) {
        this.us = r;
    }



    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody UserModel a) throws IOException, MessagingException {
        int resp = us.isExist(a);
        if(resp == 1){ //0 if not existed 1 for username duplicate and 2 for email
            return new ResponseEntity<ErrorModel>(new ErrorModel("Username Already Existed"), HttpStatus.resolve(409));
        }
        else if(resp == 2){
            return new ResponseEntity<ErrorModel>(new ErrorModel("Email Already Existed"), HttpStatus.resolve(409));
        }
        else{
            a.setUserId(UUID.randomUUID().toString());
            es.verifymail(a.getEmail(),a.getUserId());
            return new ResponseEntity<UserModel>(this.us.saveUSer(a), HttpStatus.resolve(200));
        }
    }

//    @GetMapping
//    public Double get(){
//        ArrayList<Double> XData = new ArrayList<>() ;
//        ArrayList<Double> YData = new ArrayList<>() ;
//
//        XData.add( 2015.0 ) ;
//        XData.add( 2016.0 ) ;
//        XData.add( 2017.0 ) ;
//        XData.add( 2018.0 ) ;
//        XData.add( 2019.0 ) ;
//
//        YData.add( 2000.0 ) ;
//        YData.add( 1100.0 ) ;
//        YData.add( 800.0 ) ;
//        YData.add( 1800.0 ) ;
//        YData.add( 4000.0 ) ;
//
//        double[] x = new double[]{9,10,11,12,13};
//        double[] y = new double[]{117,128,137,147,140};
//
//        LinearRegression o = new LinearRegression(x,y);
//        return  o.predict(14);
//    }
}
