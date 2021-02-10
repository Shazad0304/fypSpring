package com.example.crypto.Controllers;


import com.example.crypto.Hashing.MD5;
import com.example.crypto.Model.ErrorModel;
import com.example.crypto.Model.UserModel;
import com.example.crypto.services.emailService;
import com.example.crypto.services.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
@RequestMapping("/forget")
public class ForgetController {

    @Autowired
    private userService us;

    @Autowired
    private emailService es;

    @PostMapping
    public ResponseEntity<?> forget(@RequestBody  UserModel a) throws IOException, MessagingException {
        if(us.isEmailExist(a.getEmail()) == null){
            return new ResponseEntity<ErrorModel>(new ErrorModel("Invalid Email Address"), HttpStatus.NOT_FOUND);
        }
        else{
            es.sendmail(a.getEmail());
            return new ResponseEntity<String>("Email sent Successfully", HttpStatus.OK);
        }
    }

    @PostMapping("/checkCode")
    public ResponseEntity<?> checkCode(@RequestBody UserModel a) throws IOException, MessagingException {
        UserModel um = us.checkCode(a.getCode());
            return um == null ?
                    new ResponseEntity<ErrorModel>(new ErrorModel("Invalid Code"), HttpStatus.NOT_FOUND):
                    new ResponseEntity<UserModel>(um, HttpStatus.OK);
    }

    @PutMapping("/save")
    public ResponseEntity<?> saveupdate(@RequestBody UserModel a) throws IOException, MessagingException {
                a.setPassword(MD5.getMd5(a.getPassword()));
             return new ResponseEntity<UserModel>(this.us.saveUSer(a), HttpStatus.OK);
    }
}
