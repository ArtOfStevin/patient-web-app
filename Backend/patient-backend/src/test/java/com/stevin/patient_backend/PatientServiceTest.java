package com.stevin.patient_backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.stevin.patient_backend.dto.PatientRequest;
import com.stevin.patient_backend.dto.PatientResponse;
import com.stevin.patient_backend.entity.Patient;
import com.stevin.patient_backend.exception.DuplicateResourceException;
import com.stevin.patient_backend.exception.ResourceNotFoundException;
import com.stevin.patient_backend.repository.PatientRepository;
import com.stevin.patient_backend.service.PatientService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {
    
    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    private Patient patient;
    private PatientRequest request;

    @BeforeEach
    void setUp() {
        patient = new Patient();
        patient.setPid("P001");
        patient.setFirstName("Michael");
        patient.setLastName("Stevin");
        patient.setDateOfBirth(LocalDate.of(1998, 1, 1));
        patient.setGender("Male");
        patient.setPhoneNo("0412345678");
        patient.setAddress("40 Wattle Street");
        patient.setSuburb("Sydney");
        patient.setState("NSW");
        patient.setPostcode("2210");

        request = new PatientRequest();
        request.setPid("P001");
        request.setFirstName("Michael");
        request.setLastName("Stevin");
        request.setDateOfBirth(LocalDate.of(1998, 1, 1));
        request.setGender("Male");
        request.setPhoneNo("0412345678");
        request.setAddress("40 Wattle Street");
        request.setSuburb("Sydney");
        request.setState("NSW");
        request.setPostcode("2210");
    }

    @Test
    void createPatient_shouldCreatePatientSuccessfully() {
        when(patientRepository.existsByPid("P001")).thenReturn(false);
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        PatientResponse response = patientService.createPatient(request);

        assertEquals("P001", response.getPid());
        assertEquals("Michael", response.getFirstName());
        assertEquals("Stevin", response.getLastName());
        assertEquals("40 Wattle Street", response.getAddress());
        assertEquals("2210", response.getPostcode());

        verify(patientRepository).existsByPid("P001");
        verify(patientRepository).save(any(Patient.class));
    }

    @Test
    void createPatient_shouldThrowExceptionWhenPidAlreadyExists() {
        when(patientRepository.existsByPid("P001")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () ->
                patientService.createPatient(request)
        );

        verify(patientRepository).existsByPid("P001");
        verify(patientRepository, never()).save(any(Patient.class));
    }

    @Test
    void getPatientById_shouldReturnPatientWhenFound() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        PatientResponse response = patientService.getPatientById(1L);

        assertEquals("P001", response.getPid());
        assertEquals("Michael", response.getFirstName());
        assertEquals("Stevin", response.getLastName());

        verify(patientRepository).findById(1L);
    }

    @Test
    void getPatientById_shouldThrowExceptionWhenNotFound() {
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                patientService.getPatientById(1L)
        );

        verify(patientRepository).findById(1L);
    }

    @Test
    void updatePatient_shouldUpdatePatientSuccessfully() {
        PatientRequest updateRequest = new PatientRequest();
        updateRequest.setPid("P001");
        updateRequest.setFirstName("Michael");
        updateRequest.setLastName("Stevin");
        updateRequest.setDateOfBirth(LocalDate.of(1998, 1, 1));
        updateRequest.setGender("Male");
        updateRequest.setPhoneNo("0499888777");
        updateRequest.setAddress("40 Wattle Street");
        updateRequest.setSuburb("Sydney");
        updateRequest.setState("NSW");
        updateRequest.setPostcode("2210");

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(patientRepository.save(any(Patient.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PatientResponse response = patientService.updatePatient(1L, updateRequest);

        assertEquals("P001", response.getPid());
        assertEquals("Michael", response.getFirstName());
        assertEquals("Stevin", response.getLastName());
        assertEquals("0499888777", response.getPhoneNo());
        assertEquals("40 Wattle Street", response.getAddress());
        assertEquals("2210", response.getPostcode());

        verify(patientRepository).findById(1L);
        verify(patientRepository).save(patient);
    }

    @Test
    void updatePatient_shouldThrowExceptionWhenPatientNotFound() {
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                patientService.updatePatient(1L, request)
        );

        verify(patientRepository).findById(1L);
        verify(patientRepository, never()).save(any(Patient.class));
    }

    @Test
    void deletePatient_shouldDeletePatientSuccessfully() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        patientService.deletePatient(1L);

        verify(patientRepository).findById(1L);
        verify(patientRepository).delete(patient);
    }

    @Test
    void deletePatient_shouldThrowExceptionWhenPatientNotFound() {
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                patientService.deletePatient(1L)
        );

        verify(patientRepository).findById(1L);
        verify(patientRepository, never()).delete(any(Patient.class));
    }
}
