package com.neo.bharat.spring.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "project")
@Data
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long projectId;

    @Column(name = "projectName")
    private String projectName;

    @Column(name = "projectDuration")
    private int projectDuration;

    @ManyToOne
    private Student student;

}
