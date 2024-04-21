import { Medicine } from '../../admin/medicine-management/medicine-management.model';

export class MedicinePrescriptionModel {
  constructor(
    public id?: number,
    public medicineId?: number,
    public medicine?: Medicine,
    public prescriptionId?: number,
    public description?: string,
    public price?: number,
    public amount?: number
  ) {}
}
