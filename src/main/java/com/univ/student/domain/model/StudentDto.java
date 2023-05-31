package com.univ.student.domain.model;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;

@Data
@Builder
public class StudentDto {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private Instant admissionDate = Instant.now();
    private String registrationId;
    private String departmentName;
    private String courseName;
}
