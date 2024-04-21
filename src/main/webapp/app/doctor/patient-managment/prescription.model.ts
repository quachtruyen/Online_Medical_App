import { MedicinePrescriptionModel } from './medicine-prescription.model';

export class PrescriptionModel {
  constructor(public id?: number, public doctorId?: number, public medicineDTOs?: MedicinePrescriptionModel[], public createdAt?: Date) {}
}
