package com.example.crypto.Controllers;


import com.example.crypto.Hashing.MD5;
import com.example.crypto.ML.LinearRegression;
import com.example.crypto.ML.LinearRegressionClassifier;
import com.example.crypto.Model.CoinHistory;
import com.example.crypto.Model.ErrorModel;
import com.example.crypto.Model.UserModel;
import com.example.crypto.mongorepo.connection;
import com.example.crypto.services.Polenix;
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
import java.util.*;

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
            a.setPassword(MD5.getMd5(a.getPassword()));
            es.verifymail(a.getEmail(),a.getUserId());
            return new ResponseEntity<UserModel>(this.us.saveUSer(a), HttpStatus.resolve(200));
        }
    }


}
