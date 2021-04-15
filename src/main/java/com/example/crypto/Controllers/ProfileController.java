package com.example.crypto.Controllers;


import com.example.crypto.Hashing.MD5;
import com.example.crypto.JWT.JwtTokenUtil;
import com.example.crypto.Model.Profile;
import com.example.crypto.Model.UserModel;
import com.example.crypto.services.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private userService us;

    @Autowired
    private JwtTokenUtil jwt;

    @GetMapping
    public UserModel getProfile(@RequestHeader("Authorization") String token){
            String id = jwt.getIdFromToken(token.split(" ")[1]);
            return this.us.getByid(id);
    }

    @PutMapping
    public ResponseEntity<?> updateProfile(@Valid @RequestBody UserModel us){
        UserModel temp = this.us.getByid(us.getUserId());
        temp.setEmail(us.getEmail());
        temp.setFirstName(us.getFirstName());
        temp.setLastName(us.getLastName());
        if(us.getPassword() != null){
            temp.setPassword(MD5.getMd5(us.getPassword()));
        }
        return new ResponseEntity<UserModel>(this.us.saveUSer(temp), HttpStatus.resolve(200));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProfileAndPass(@RequestHeader("Authorization") String token,@Valid @RequestBody Profile us){
        String id = jwt.getIdFromToken(token.split(" ")[1]);
        UserModel temp = this.us.getByid(id);
        temp.setEmail(us.getEmail());
        temp.setFirstName(us.getFirstName());
        temp.setLastName(us.getLastName());
        if(us.getNewPassword() != null){
            String hashOldPass = MD5.getMd5(us.getOldPassword());
            if(temp.getPassword() == hashOldPass){
                temp.setPassword(MD5.getMd5(us.getNewPassword()));
            }
            else{
                return ResponseEntity.status(400).body("Old password incorrect");
            }
        }
        return new ResponseEntity<UserModel>(this.us.saveUSer(temp), HttpStatus.resolve(200));
    }
}
