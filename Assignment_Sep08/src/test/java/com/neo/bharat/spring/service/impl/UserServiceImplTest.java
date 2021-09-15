package com.neo.bharat.spring.service.impl;

import com.neo.bharat.spring.dto.UserRequestBody;
import com.neo.bharat.spring.entity.User;
import com.neo.bharat.spring.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        //userService = new UserServiceImpl();
    }

    @Test
    public void testAddUserWhenBodyMissingParameters() {
        UserRequestBody userRequestBody = new UserRequestBody();
        ResponseEntity<UserRequestBody> userRequestBodyresponse = userService.addUser(userRequestBody);
        assertEquals(400, userRequestBodyresponse.getStatusCodeValue());
    }

    @Test
    public void testAddUserWhenDOBIsInvalidFormat() {
        UserRequestBody userRequestBody = new UserRequestBody();
        userRequestBody.setUserId("1");
        userRequestBody.setDob("01012");
        userRequestBody.setJoiningDate("19880114");
        userRequestBody.setMobile("9876543210");
        userRequestBody.setPincode("500018");
        userRequestBody.setFirstName("Bharat");
        userRequestBody.setSurName("G");
        userRequestBody.setAddress("Hyderabad");
        userRequestBody.setUserName("Bhart.g");
        ResponseEntity<UserRequestBody> userRequestBodyresponse = userService.addUser(userRequestBody);
        assertEquals(422, userRequestBodyresponse.getStatusCodeValue());
    }

    @Test
    public void testAddUser() {
        UserRequestBody userRequestBody =createUserRequestBody();
        User user = craeteUser();
        when(userRepository.save(any(User.class))).thenReturn(user);
        ResponseEntity<UserRequestBody> userRequestBodyresponse = userService.addUser(userRequestBody);
        assertEquals(200, userRequestBodyresponse.getStatusCodeValue());
    }

    @Test
    public void testSearchUserWhenAllParametersAreEmpty(){
        ResponseEntity<List<UserRequestBody>> response =  userService.searchUser(null, null, null);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testSearchUserWhenFirstNameIsNotEmpty(){
        List<User> userList = new ArrayList<>();
        User user = craeteUser();
        userList.add(user);
        when(userRepository.findAllByFirstName(anyString())).thenReturn(userList);
        ResponseEntity<List<UserRequestBody>> response =  userService.searchUser("Bharat", null, null);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(userRepository, never()).findAllByPincode(anyString());
    }

    @Test
    public void testSearchUserWhenSurNameIsNotEmpty(){
        List<User> userList = new ArrayList<>();
        User user = craeteUser();
        userList.add(user);
        when(userRepository.findAllBySurName(anyString())).thenReturn(userList);
        ResponseEntity<List<UserRequestBody>> response =  userService.searchUser(null, "G", null);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(userRepository, never()).findAllByFirstName(anyString());
    }

    @Test
    public void testSearchUserWhenPinCodeIsNotEmpty(){
        List<User> userList = new ArrayList<>();
        User user = craeteUser();
        userList.add(user);
        when(userRepository.findAllByPincode(anyString())).thenReturn(userList);
        ResponseEntity<List<UserRequestBody>> response =  userService.searchUser(null, null, "500018");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(userRepository, never()).findAllBySurName(anyString());
    }

    @Test
    public void testSearchUserWhenNoDataIsAvailable(){
        List<User> userList = new ArrayList<>();
        User user = craeteUser();
        userList.add(user);
        when(userRepository.findAllByFirstName(anyString())).thenReturn(null);
        when(userRepository.findAllBySurName(anyString())).thenReturn(null);
        when(userRepository.findAllByPincode(anyString())).thenReturn(null);
        ResponseEntity<List<UserRequestBody>> response =  userService.searchUser("Bharat", "G", "500018");
        assertEquals(404, response.getStatusCodeValue());
        verify(userRepository, times(1)).findAllByFirstName(anyString());
        verify(userRepository, times(1)).findAllBySurName(anyString());
        verify(userRepository, times(1)).findAllByPincode(anyString());
    }

    @Test
    public void testDeleteUserWhenNoDataAvailable(){
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());
        ResponseEntity<String> response = userService.deleteUser(1, "1");
        assertEquals("No Data Found for User ID:1", response.getBody());
    }

    @Test
    public void testDeleteUserWhenSoftDelete(){
        User user = craeteUser();
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        ResponseEntity<String> response = userService.deleteUser(0, "1");
        assertEquals("User Soft Delete is Done", response.getBody());
    }

    @Test
    public void testDeleteUserWhenHardDelete(){
        User user = craeteUser();
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        ResponseEntity<String> response = userService.deleteUser(1, "1");
        assertEquals("User Hard Delete is Done", response.getBody());
    }

    private UserRequestBody createUserRequestBody(){
        UserRequestBody userRequestBody = new UserRequestBody();
        userRequestBody.setUserId("1");
        userRequestBody.setDob("19880114");
        userRequestBody.setJoiningDate("19880114");
        userRequestBody.setMobile("9876543210");
        userRequestBody.setPincode("500018");
        userRequestBody.setFirstName("Bharat");
        userRequestBody.setSurName("G");
        userRequestBody.setAddress("Hyderabad");
        userRequestBody.setUserName("Bhart.g");
        return userRequestBody;
    }

    private User craeteUser(){
        User user = new User();
        user.setUserId(1);
        user.setIsActive(1);
        user.setPincode("500018");
        user.setDob(new Date());
        user.setSurName("G");
        user.setJoiningDate(new Date());
        user.setAddress("Hyderabad");
        return user;
    }
}


