package com.example.jwt.controller;

import com.example.jwt.dto.request.SigUpForm;
import com.example.jwt.dto.respone.ResponeMessage;
import com.example.jwt.model.Role;
import com.example.jwt.model.RoleName;
import com.example.jwt.model.User;
import com.example.jwt.service.impl.RoleServiceImpl;
import com.example.jwt.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RequestMapping("/api/auth")
@RestController
public class AuthController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    RoleServiceImpl roleService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    ResponseEntity<?> register(@Valid @RequestBody SigUpForm sigUpForm){
        if (userService.existsUserByUserName(sigUpForm.getUsername())){
            return new ResponseEntity<>(new ResponeMessage("The username is existed"), HttpStatus.OK);
        }
        if (userService.existsUserByEmail(sigUpForm.getEmail())){
            return new ResponseEntity<>(new ResponeMessage("The email is existed"), HttpStatus.OK);
        }
        User user = new User(sigUpForm.getName(), sigUpForm.getUsername(), sigUpForm.getEmail(), passwordEncoder.encode(sigUpForm.getPassword()));
        Set<String> strRoles = sigUpForm.getRoles();
        Set<Role> roles = new HashSet<>();
        strRoles.forEach(role ->{
            switch (role){
                case "admin":
                    Role adminRole = roleService.findByName(RoleName.ADMIN).orElseThrow( () -> new RuntimeException("Role not found"));
                    roles.add(adminRole);
                    break;
                case "pm":
                    Role pmRole = roleService.findByName(RoleName.PM).orElseThrow( () -> new RuntimeException("Role not found"));
                    roles.add(pmRole);
                    break;
                default:
                    Role userRole = roleService.findByName(RoleName.USER).orElseThrow( () -> new RuntimeException("Role not found"));
                    roles.add(userRole);
            }
        });
        user.setRoles(roles);
        userService.save(user);
        return new ResponseEntity<>(new ResponeMessage("Create success"), HttpStatus.OK);
    }
}
