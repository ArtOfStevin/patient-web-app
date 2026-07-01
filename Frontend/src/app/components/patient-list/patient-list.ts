import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatIconModule } from '@angular/material/icon';

import { Patient } from '../../models/patient';
import { PatientService } from '../../services/patient.service';

@Component({
  selector: 'app-patient-list',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatTableModule,
    MatButtonModule,
    MatInputModule,
    MatFormFieldModule,
    MatPaginatorModule,
    MatIconModule
  ],
  templateUrl: './patient-list.html',
  styleUrl: './patient-list.css'
})
export class PatientList implements OnInit {
  patients: Patient[] = [];

  displayedColumns: string[] = [
    'pid',
    'firstName',
    'lastName',
    'dateOfBirth',
    'gender',
    'phoneNo',
    'suburb',
    'state',
    'postcode',
    'actions'
  ];

  searchText = '';

  pageIndex = 0;
  pageSize = 10;
  totalElements = 0;

  loading = false;

  constructor(
    private patientService: PatientService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadPatients();
  }

  loadPatients(): void {
    this.loading = true;

    this.patientService.getPatients(this.searchText, this.pageIndex, this.pageSize)
      .subscribe({
        next: (page) => {
          this.patients = page.content;
          this.totalElements = page.totalElements;
          this.pageIndex = page.number;
          this.pageSize = page.size;
          this.loading = false;
        },
        error: (error) => {
          console.error('Failed to load patients', error);
          this.loading = false;
        }
      });
  }

  onSearch(): void {
    this.pageIndex = 0;
    this.loadPatients();
  }

  clearSearch(): void {
    this.searchText = '';
    this.pageIndex = 0;
    this.loadPatients();
  }

  onPageChange(event: PageEvent): void {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadPatients();
  }

  createPatient(): void {
    this.router.navigate(['/patients/new']);
  }

  editPatient(patient: Patient): void {
    this.router.navigate(['/patients/edit', patient.id]);
  }

  deletePatient(patient: Patient): void {
    if (!patient.id) {
      return;
    }

    const confirmed = confirm(`Delete patient ${patient.firstName} ${patient.lastName}?`);

    if (confirmed) {
      this.patientService.deletePatient(patient.id).subscribe({
        next: () => this.loadPatients(),
        error: (error) => console.error('Failed to delete patient', error)
      });
    }
  }
}
