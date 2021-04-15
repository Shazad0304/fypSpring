package com.example.crypto.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    private String email;
    private String firstName;
    private String lastName;
    private String oldPassword;
    private String newPassword;
}
