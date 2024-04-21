import { Doctor } from './doctor.model';

export class Assignment {
  constructor(
    public id?: number,
    public doctorId?: number,
    public patientId?: number,
    public patientStatus?: string,
    public createdBy?: string,
    public createdDate?: Date,
    public assignDate?: Date,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Date
  ) {}
}
