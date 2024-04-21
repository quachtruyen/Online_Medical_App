import { MedicinePrescriptionModel } from './medicine-prescription.model';

export class DoctorAction {
  constructor(
    public id?: number,
    public dailyHealthStatusId?: number,
    public advise?: string,
    public note?: string,
    public medicines?: MedicinePrescriptionModel[],
    public createdAt?: Date,
    public prescriptionId?: number
  ) {}
}
