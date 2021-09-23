package com.neo.bharat.spring.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties("projectid")
public class ProjectRequestBody {
    @JsonProperty("projectid")
    private String projectId;

    @JsonProperty("projectName")
    private String projectName;

    @JsonProperty("projectDuration")
    private int projectDuration;


}
