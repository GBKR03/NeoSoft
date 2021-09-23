package com.neo.bharat.spring.service.impl;

import com.neo.bharat.spring.dto.ProjectRequestBody;
import com.neo.bharat.spring.dto.StudentRequestBody;
import com.neo.bharat.spring.entity.Project;
import com.neo.bharat.spring.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.neo.bharat.spring.repository.StudentRepository;
import com.neo.bharat.spring.service.StudentService;
import org.springframework.util.CollectionUtils;

import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService{

	@Autowired
	StudentRepository studentRepository;

	@Override
	public ResponseEntity<StudentRequestBody> addStudent(StudentRequestBody studentRequestBody){
		Student student = studentRepository.save(convertStudentRequestBodyToStudent(studentRequestBody));
		StudentRequestBody studentRequestBody1 = convertStudentToStudentRequestBody(student);
		return ResponseEntity.of(Optional.of(studentRequestBody1));

	}

	@Override
	public ResponseEntity<StudentRequestBody> findAllStudentId(String studentId){
		Optional<Student> student =  studentRepository.findById(Integer.valueOf(studentId));
		if(student.isPresent()){
			StudentRequestBody studentRequestBody = convertStudentToStudentRequestBody(student.get());
			return ResponseEntity.ok(studentRequestBody);
		}else{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}

	}

	@Override
	public ResponseEntity<List<StudentRequestBody>> findAllStudents(){
		List<Student> students =  studentRepository.findAll();
		if(CollectionUtils.isEmpty(students)){
			List<StudentRequestBody> studentRequestBodyList = new ArrayList<>();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(studentRequestBodyList);
		}else{
			List<StudentRequestBody> studentRequestBodyList = new ArrayList<>();
			try{
				for(Student student : students){
					StudentRequestBody studentRequestBody = convertStudentToStudentRequestBody(student);
					studentRequestBodyList.add(studentRequestBody);
				}
			}catch(Exception e){
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(studentRequestBodyList);
			}
			return ResponseEntity.ok(studentRequestBodyList);
		}
	}

	private Student convertStudentRequestBodyToStudent(StudentRequestBody studentRequestBody){
		Student student = new Student();
		student.setEmail(studentRequestBody.getEmail());
		student.setFirstName(studentRequestBody.getFirstName());
		student.setLastName(studentRequestBody.getLastName());
		student.setMobileNumber(studentRequestBody.getMobileNumber());
		student.setProjectList(Arrays.asList(
				convertProjectRequestBodyToProject(studentRequestBody.getProject(), student)));
		return student;
	}

	private Project convertProjectRequestBodyToProject(ProjectRequestBody projectRequestBody,
													   Student student){
		Project project = new Project();
		project.setProjectName(projectRequestBody.getProjectName());
		project.setProjectDuration(projectRequestBody.getProjectDuration());
		project.setStudent(student);
		return project;
	}


	private StudentRequestBody convertStudentToStudentRequestBody(Student student){
		StudentRequestBody studentRequestBody = new StudentRequestBody();
		studentRequestBody.setStudentId(String.valueOf(student.getStudentId()));
		studentRequestBody.setEmail(student.getEmail());
		studentRequestBody.setFirstName(student.getFirstName());
		studentRequestBody.setLastName(student.getLastName());
		studentRequestBody.setMobileNumber(student.getMobileNumber());
		studentRequestBody.setProject(convertProjectToProjectRequest(student.getProjectList(), studentRequestBody ));
		return studentRequestBody;
	}

	private ProjectRequestBody convertProjectToProjectRequest(List<Project> project,
															  StudentRequestBody studentRequestBody){
		if(!CollectionUtils.isEmpty(project)){
			Project project1 = project.get(0);
			ProjectRequestBody projectRequestBody = new ProjectRequestBody();
			projectRequestBody.setProjectId(String.valueOf(project1.getProjectId()));
			projectRequestBody.setProjectName(project1.getProjectName());
			projectRequestBody.setProjectDuration(project1.getProjectDuration());
			return projectRequestBody;
		}
		return null;

	}



}
