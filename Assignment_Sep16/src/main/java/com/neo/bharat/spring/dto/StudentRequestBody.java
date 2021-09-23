package com.neo.bharat.spring.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.neo.bharat.spring.entity.Project;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
@JsonIgnoreProperties("studentid")
public class StudentRequestBody {

    @JsonProperty("studentid")
    private String studentId;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("mobileNumber")
    private String mobileNumber;

    @JsonProperty("email")
    private String email;

    @JsonProperty("project")
    private ProjectRequestBody project;

}
