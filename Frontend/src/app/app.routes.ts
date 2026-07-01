import { Routes } from '@angular/router';
import { PatientList } from './components/patient-list/patient-list';
import { PatientForm } from './components/patient-form/patient-form';

export const routes: Routes = [
    { path: '', redirectTo: 'patients', pathMatch: 'full' },
    { path: 'patients', component: PatientList },
    { path: 'patients/new', component: PatientForm },
    { path: 'patients/edit/:id', component: PatientForm }
];
