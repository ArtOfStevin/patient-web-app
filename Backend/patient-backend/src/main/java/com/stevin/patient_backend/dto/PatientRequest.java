package com.stevin.patient_backend.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PatientRequest {
    @NotBlank(message = "PID is required")
    @Size(max = 50)
    private String pid;

    @NotBlank(message = "First name is required")
    @Size(max = 100)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 100)
    private String lastName;

    private LocalDate dateOfBirth;

    @Size(max = 20)
    private String gender;

    @Size(max = 20)
    private String phoneNo;

    @Size(max = 255)
    private String address;

    @Size(max = 100)
    private String suburb;

    @Size(max = 50)
    private String state;

    @Size(max = 10)
    private String postcode;
}
