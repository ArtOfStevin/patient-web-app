package com.stevin.patient_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PatientResponse {
    private Long id;
    private String pid;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String phoneNo;
    private String address;
    private String suburb;
    private String state;
    private String postcode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
