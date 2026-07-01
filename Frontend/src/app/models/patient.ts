export interface Patient {
    id?: number;
    pid: string;
    firstName: string;
    lastName: string;
    dateOfBirth: string;
    gender: string;
    phoneNo: string;
    address: string;
    suburb: string;
    state: string;
    postcode: string;
    createdAt?: string;
    updatedAt?: string;
}

export interface PatientPage {
  content: Patient[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}
