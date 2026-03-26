package com.studentmanagement.sms_api.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private Long hours;

    @Column(name = "course_level", nullable = false)
    private Integer level;

    @Column(name = "description", nullable = false)
    private String description;
}
