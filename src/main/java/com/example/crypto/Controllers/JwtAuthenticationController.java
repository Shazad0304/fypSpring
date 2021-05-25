package com.example.crypto.Controllers;

import com.example.crypto.Hashing.MD5;
import com.example.crypto.JWT.JwtTokenUtil;
import com.example.crypto.Logger.Logging;
import com.example.crypto.Model.*;
import com.example.crypto.services.alertService;
import com.example.crypto.services.userService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    private Logger logger = Logging.getLogger(JwtAuthenticationController.class);

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailsService userDetailsService;


    @Autowired
    private userService us;

    @Autowired
    private alertService as;


    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername() == null ?
                        authenticationRequest.getEmail() :
                        authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        if(us.getByCredentials(authenticationRequest.getUsername(), MD5.getMd5(authenticationRequest.getPassword()),authenticationRequest.getEmail()) == null){
            logger.error("User Not Found:"+authenticationRequest.getUsername());
            return new ResponseEntity<ErrorModel>(new ErrorModel("User Not Found"), HttpStatus.UNAUTHORIZED);
        }
        else{
            return ResponseEntity.ok(new JwtResponse(token));
        }
    }








}
