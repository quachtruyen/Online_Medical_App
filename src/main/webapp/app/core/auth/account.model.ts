import { IPatient } from '../../doctor/patient-managment/patient-management.model';
import { Doctor } from '../../admin/user-management/doctor.model';

export class Account {
  constructor(
    public activated: boolean,
    public authorities: string[],
    public email: string,
    public position: string,
    public hospital: string,
    public specialize: string,
    public address: string,
    public healthStatus: string,
    public diseaseSymptoms: string,
    public prescription: string,
    public firstName: string | null,
    public langKey: string,
    public lastName: string | null,
    public login: string,
    public imageUrl: string | null,
    public patient: IPatient,
    public doctor: Doctor,
    public phoneNumber: string
  ) {}
}
