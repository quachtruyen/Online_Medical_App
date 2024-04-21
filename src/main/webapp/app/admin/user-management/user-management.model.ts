import { Assignment } from './assignment.model';
import { Patient } from './patient.model';
import { Doctor } from './doctor.model';
import { IPatient } from '../../doctor/patient-managment/patient-management.model';

export interface IUser {
  id?: number;
  login?: string;
  firstName?: string | null;
  lastName?: string | null;
  address?: string | null;
  healthStatus?: string | null;
  diseaseSymptoms?: string | null;
  prescription?: string | null;
  position?: string | null;
  hospital?: string | null;
  specialze?: string | null;
  email?: string;
  activated?: boolean;
  langKey?: string;
  authorities?: string[];
  createdBy?: string;
  createdDate?: Date;
  lastModifiedBy?: string;
  lastModifiedDate?: Date;
}

export class User implements IUser {
  constructor(
    public id?: number,
    public login?: string,
    public firstName?: string | null,
    public numberPatients?: number,
    public numberCaredPatients?: number,
    public lastName?: string | null,
    public address?: string | null,
    public healthStatus?: string | null,
    public diseaseSymptoms?: string | null,
    public prescription?: string | null,
    public position?: string | null,
    public hospital?: string | null,
    public specialize?: string | null,
    public email?: string,
    public activated?: boolean,
    public langKey?: string,
    public authorities?: string[],
    public createdBy?: string,
    public createdDate?: Date,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Date,
    public patientStatus?: String,
    public phoneNumber?: String,
    public assignmentId?: number,
    public assignment?: Assignment,
    public patient?: IPatient,
    public imageCertificate?: string,
    public doctor?: Doctor
  ) {}
}
