package com.neo.bharat.spring.service;

import com.neo.bharat.spring.dto.StudentRequestBody;
import org.springframework.http.ResponseEntity;

import javax.xml.ws.Response;
import java.util.List;

public interface StudentService {

    ResponseEntity<StudentRequestBody> addStudent(StudentRequestBody studentRequestBody);

    ResponseEntity<StudentRequestBody> findAllStudentId(String studentId);

    ResponseEntity<List<StudentRequestBody>> findAllStudents();

}
