import { Assignment } from './assignment.model';
import { IPatient } from '../../doctor/patient-managment/patient-management.model';

export class Doctor {
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
    public assignment?: Assignment,
    public doctor?: Doctor,
    public patient?: IPatient
  ) {}
}
