package com.stevin.patient_backend.repository;

import com.stevin.patient_backend.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Page<Patient> findByPidContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
            String pid,
            String firstName,
            String lastName,
            Pageable pageable
    );

    boolean existsByPid(String pid);
    
}
