package com.neo.bharat.spring.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
@JsonIgnoreProperties("userid")
public class UserRequestBody {

    @JsonProperty("userid")
    private String userId;

    @JsonProperty("username")
    private String userName;

    @JsonProperty("dob")
    private String dob;

    @JsonProperty("joiningDate")
    private String joiningDate;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("surName")
    private String surName;

    @JsonProperty("mobile")
    private String mobile;

    @JsonProperty("address")
    private String address;

    @JsonProperty("pincode")
    private String pincode;

}
