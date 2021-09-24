package com.neo.bharat.spring.controller;

import com.neo.bharat.spring.dto.StudentRequestBody;
import com.neo.bharat.spring.security.OAuthDao;
import com.neo.bharat.spring.security.UserEntity;
import com.neo.bharat.spring.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;

@RestController
public class StudentController {

    private static final String ADMIN = "admin";
    @Autowired
    private StudentService studentService;

    @Autowired
    OAuthDao oAuthDao;

    @PostMapping("/student")
    //@PreAuthorize
    public ResponseEntity<StudentRequestBody> createStudent(@RequestBody StudentRequestBody requestBody,
                                                            @RequestHeader String userName){
        UserEntity userEntity = oAuthDao.getUserDetails(userName);
        if(userEntity == null){
        //if(userName == null || oAuthDao.getUserDetails(userName) == null || ()){
           return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        return studentService.addStudent(requestBody);
    }

    @GetMapping("/student/eachEach")
    //@PreAuthorize()
    public ResponseEntity<StudentRequestBody> getStudentByStudentId(@RequestParam("studentId") String studentId,
                                                                    @RequestHeader String userName){
        UserEntity userEntity = oAuthDao.getUserDetails(userName);
        if(userEntity == null || (userEntity != null && userEntity.getGrantedAuthoritiesList().contains("ADMIN"))){

//            if(ADMIN.equals(roleId)){
            return studentService.findAllStudentId(studentId);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);

    }

    @GetMapping("/student")
    //@PreAuthorize()
    public ResponseEntity<List<StudentRequestBody>> getStudents(
            @RequestHeader String userName){
        UserEntity userEntity = oAuthDao.getUserDetails(userName);
        if(userEntity == null || (userEntity != null && userEntity.getGrantedAuthoritiesList().contains("ADMIN"))){
            return studentService.findAllStudents();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

	
}
