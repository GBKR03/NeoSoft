package com.neo.bharat.spring.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
@Entity
@Table(name = "student")
@Data
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long studentId;

	@Column(name = "firstName")
	private String firstName;
	
	@Column(name = "lastName")
	private String lastName;

	@Size(max=10)
	@Column(name = "mobileNumber")
	private String mobileNumber;
	
	@Column(name = "email")
	private String email;
	
	@OneToMany
	private List<Project> projectList;
	
	@Column(name = "isactive")
	private int isActive;
	
	
	
	
	
	
	
	
}
