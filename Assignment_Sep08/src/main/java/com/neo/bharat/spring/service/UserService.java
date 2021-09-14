package com.neo.bharat.spring.service;

import com.neo.bharat.spring.dto.UserRequestBody;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    ResponseEntity<UserRequestBody> addUser(UserRequestBody userRequestBody);

    ResponseEntity<List<UserRequestBody>> searchUser(String firstName, String surname, String pinCode);
    ResponseEntity<String> deleteUser(int softDelete, String userId);
}
