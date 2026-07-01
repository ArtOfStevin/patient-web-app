import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatCardModule } from '@angular/material/card';

import { PatientService } from '../../services/patient.service';
import { Patient } from '../../models/patient';

@Component({
  selector: 'app-patient-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatInputModule,
    MatFormFieldModule,
    MatCardModule
  ],
  templateUrl: './patient-form.html',
  styleUrl: './patient-form.css'
})
export class PatientForm implements OnInit {
   patientId?: number;
  isEditMode = false;
  loading = false;
  errorMessage = '';

  patientForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private patientService: PatientService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.patientForm = this.formBuilder.group({
      pid: ['', [Validators.required, Validators.maxLength(50)]],
      firstName: ['', [Validators.required, Validators.maxLength(100)]],
      lastName: ['', [Validators.required, Validators.maxLength(100)]],
      dateOfBirth: [''],
      gender: ['', [Validators.maxLength(20)]],
      phoneNo: ['', [Validators.maxLength(20)]],
      address: ['', [Validators.maxLength(255)]],
      suburb: ['', [Validators.maxLength(100)]],
      state: ['', [Validators.maxLength(50)]],
      postcode: ['', [Validators.maxLength(10)]]
    });
  }

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');

    if (idParam) {
      this.patientId = Number(idParam);
      this.isEditMode = true;
      this.loadPatient(this.patientId);
    }
  }

  loadPatient(id: number): void {
    this.loading = true;

    this.patientService.getPatientById(id).subscribe({
      next: (patient) => {
        this.patientForm.patchValue({
          pid: patient.pid,
          firstName: patient.firstName,
          lastName: patient.lastName,
          dateOfBirth: patient.dateOfBirth,
          gender: patient.gender,
          phoneNo: patient.phoneNo,
          address: patient.address,
          suburb: patient.suburb,
          state: patient.state,
          postcode: patient.postcode
        });

        this.loading = false;
      },
      error: (error) => {
        console.error('Failed to load patient', error);
        this.errorMessage = 'Failed to load patient.';
        this.loading = false;
      }
    });
  }

  savePatient(): void {
    if (this.patientForm.invalid) {
      this.patientForm.markAllAsTouched();
      return;
    }

    const patient = this.patientForm.value as Patient;
    this.loading = true;
    this.errorMessage = '';

    if (this.isEditMode && this.patientId) {
      this.patientService.updatePatient(this.patientId, patient).subscribe({
        next: () => {
          this.loading = false;
          this.router.navigate(['/patients']);
        },
        error: (error) => {
          console.error('Failed to update patient', error);
          this.errorMessage = error.error?.message || 'Failed to update patient.';
          this.loading = false;
        }
      });
    } else {
      this.patientService.createPatient(patient).subscribe({
        next: () => {
          this.loading = false;
          this.router.navigate(['/patients']);
        },
        error: (error) => {
          console.error('Failed to create patient', error);
          this.errorMessage = error.error?.message || 'Failed to create patient.';
          this.loading = false;
        }
      });
    }
  }

  cancel(): void {
    this.router.navigate(['/patients']);
  }
}
