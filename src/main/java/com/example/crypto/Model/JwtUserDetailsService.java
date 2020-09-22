package com.example.crypto.Model;

import com.example.crypto.mongorepo.UserModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class JwtUserDetailsService {
    private String userName;
    private String password;



    public UserDetails loadUserByUsername(String a){
        UserModel user = new UserModel();
        String[] ab = {"manager"};
        User.UserBuilder builder = User.withUsername(a);
        builder.password(new BCryptPasswordEncoder().encode("password"));
        builder.roles(ab);
        return builder.build();
    }
}
