package com.example.jwt.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SigUpForm {
    private String name;
    private String username;
    private String email;
    private String password;
    private Set<String> roles;
}
