package com.stevin.patient_backend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.stevin.patient_backend.dto.PatientRequest;
import com.stevin.patient_backend.dto.PatientResponse;
import com.stevin.patient_backend.entity.Patient;
import com.stevin.patient_backend.exception.DuplicateResourceException;
import com.stevin.patient_backend.exception.ResourceNotFoundException;
import com.stevin.patient_backend.repository.PatientRepository;

@Service
public class PatientService {
    
     private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Page<PatientResponse> getPatients(String search, Pageable pageable) {
        Page<Patient> patients;

        if (search == null || search.trim().isEmpty()) {
            patients = patientRepository.findAll(pageable);
        } else {
            String keyword = search.trim();

            patients = patientRepository
                    .findByPidContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
                            keyword,
                            keyword,
                            keyword,
                            pageable
                    );
        }

        return patients.map(this::mapToResponse);
    }

    public PatientResponse getPatientById(Long id) {
        Patient patient = findPatientById(id);
        return mapToResponse(patient);
    }

    public PatientResponse createPatient(PatientRequest request) {
        if (patientRepository.existsByPid(request.getPid())) {
            throw new DuplicateResourceException("Patient PID already exists: " + request.getPid());
        }

        Patient patient = new Patient();
        applyRequestToPatient(patient, request);

        Patient savedPatient = patientRepository.save(patient);

        return mapToResponse(savedPatient);
    }

    public PatientResponse updatePatient(Long id, PatientRequest request) {
        Patient patient = findPatientById(id);

        if (!patient.getPid().equals(request.getPid()) &&
                patientRepository.existsByPid(request.getPid())) {
            throw new DuplicateResourceException("Patient PID already exists: " + request.getPid());
        }

        applyRequestToPatient(patient, request);

        Patient updatedPatient = patientRepository.save(patient);

        return mapToResponse(updatedPatient);
    }

    public void deletePatient(Long id) {
        Patient patient = findPatientById(id);
        patientRepository.delete(patient);
    }

    private Patient findPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
    }

    private void applyRequestToPatient(Patient patient, PatientRequest request) {
        patient.setPid(request.getPid());
        patient.setFirstName(request.getFirstName());
        patient.setLastName(request.getLastName());
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setGender(request.getGender());
        patient.setPhoneNo(request.getPhoneNo());
        patient.setAddress(request.getAddress());
        patient.setSuburb(request.getSuburb());
        patient.setState(request.getState());
        patient.setPostcode(request.getPostcode());
    }

    private PatientResponse mapToResponse(Patient patient) {
        return new PatientResponse(
                patient.getId(),
                patient.getPid(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getDateOfBirth(),
                patient.getGender(),
                patient.getPhoneNo(),
                patient.getAddress(),
                patient.getSuburb(),
                patient.getState(),
                patient.getPostcode(),
                patient.getCreatedAt(),
                patient.getUpdatedAt()
        );
    }
}
