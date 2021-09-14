package com.neo.bharat.spring.entity;

import lombok.Data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
@Entity
@Table(name = "user")
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userId;

	@Past
	@Column(name = "dob")
	private Date dob;
		
	@Column(name = "joiningDate")
	private Date joiningDate;


	@Column(name = "firstName")
	private String firstName;
	
	@Column(name = "surName")
	private String surName;

	@Size(max=10)
	@Column(name = "mobile")
	private String mobile;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "pincode")
	private String pincode;
	
	@Column(name = "isactive")
	private int isActive;
	
	
	
	
	
	
	
	
}
