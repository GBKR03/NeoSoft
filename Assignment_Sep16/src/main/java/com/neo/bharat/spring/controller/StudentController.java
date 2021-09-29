package com.neo.bharat.spring.controller;

import com.neo.bharat.spring.dto.StudentRequestBody;
import com.neo.bharat.spring.entity.JwtRequest;
import com.neo.bharat.spring.entity.JwtResponse;
import com.neo.bharat.spring.security.OAuthDao;
import com.neo.bharat.spring.security.UserEntity;
import com.neo.bharat.spring.service.StudentService;
import com.neo.bharat.spring.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenUtil jwtTokenUtil;


    @PostMapping("/student")
    //@PreAuthorize
    public ResponseEntity<StudentRequestBody> createStudent(@RequestBody StudentRequestBody requestBody,
                                                            @RequestHeader String userName, @RequestHeader String token){
        UserEntity userEntity = oAuthDao.getUserDetails(userName);

        if(jwtTokenUtil.validateToken(token, userEntity)){
            return studentService.addStudent(requestBody);

        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    @GetMapping("/student/eachEach")
    //@PreAuthorize()
    public ResponseEntity<StudentRequestBody> getStudentByStudentId(@RequestParam("studentId") String studentId,
                                                                    @RequestHeader String userName, @RequestHeader String token){
        UserEntity userEntity = oAuthDao.getUserDetails(userName);

        if(jwtTokenUtil.validateToken(token, userEntity) && (userEntity != null && userEntity.getGrantedAuthoritiesList().contains("ADMIN"))){
            return studentService.findAllStudentId(studentId);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);

    }

    @GetMapping("/student")
    //@PreAuthorize()
    public ResponseEntity<List<StudentRequestBody>> getStudents(
            @RequestHeader String token, @RequestHeader String userName){
        UserEntity userEntity = oAuthDao.getUserDetails(userName);

        if(jwtTokenUtil.validateToken(token, userEntity) && (userEntity != null && userEntity.getGrantedAuthoritiesList().contains("ADMIN"))){
            return studentService.findAllStudents();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        UserEntity userEntity = oAuthDao.getUserDetails(authenticationRequest.getUsername());
        if(userEntity == null){
            final String jwtoKen = null;
            return ResponseEntity.badRequest().body(new JwtResponse());
        }


        final String token = jwtTokenUtil.generateToken(userEntity);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
