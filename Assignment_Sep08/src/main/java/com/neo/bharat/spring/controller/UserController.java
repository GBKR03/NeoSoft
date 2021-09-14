package com.neo.bharat.spring.controller;

import com.neo.bharat.spring.dto.UserRequestBody;
import com.neo.bharat.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseEntity<UserRequestBody> createUser(@RequestBody UserRequestBody requestBody){
        return userService.addUser(requestBody);
    }

    @DeleteMapping("/user")
    public ResponseEntity<String> deleteUser(@RequestParam int softDelete, @RequestParam String userId){
        return userService.deleteUser(softDelete, userId);
    }

    @GetMapping("/user")
    public ResponseEntity<List<UserRequestBody>> getUser(@RequestParam @Nullable String firstName,
                                                         @RequestParam @Nullable String surName,
                                                         @RequestParam @Nullable String pinCode){
        return userService.searchUser(firstName, surName, pinCode);
    }

	
}
