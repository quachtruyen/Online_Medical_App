import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { PatientManagementService } from '../service/patient-management.service';
import { DoctorAction } from '../doctor-action.model';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MedicineManagementService } from '../../../admin/medicine-management/service/medicine-management.service';
import { ASC, DESC, ITEMS_PER_PAGE } from '../../../config/pagination.constants';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { DailyHealthStatus } from '../daily-health-status.model';
import { IMedicine } from '../../../admin/medicine-management/medicine-management.model';
import { MedicinePrescriptionModel } from '../medicine-prescription.model';
import { AuthServerProvider } from '../../../core/auth/auth-jwt.service';

@Component({
  selector: 'jhi-doctor-action-dialog',
  templateUrl: './doctor-action-dialog.component.html',
})
export class DoctorActionDialogComponent implements OnInit {
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  medicines: IMedicine[] | null = [];
  page!: number;
  predicate!: string;
  ascending!: boolean;
  search?: string;
  action: DoctorAction = {};
  medicineSelect: MedicinePrescriptionModel[] = [];
  dailyHealthStatus?: DailyHealthStatus;
  total = 0;
  editForm = this.fb.group({
    id: [],
    advise: ['', [Validators.required]],
    notes: ['', [Validators.required]],
    medicines: this.fb.array([]),
  });
  token = '';

  constructor(
    private patientManagementService: PatientManagementService,
    private fb: FormBuilder,
    private activeModal: NgbActiveModal,
    private medicineManagementService: MedicineManagementService,
    private auth: AuthServerProvider
  ) {}

  ngOnInit(): void {
    this.token = this.auth.getToken();
    this.search = '';
    this.page = 1;
    this.predicate = 'id';
    this.medicineManagementService
      .queryUser(
        {
          page: this.page - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
        },
        this.search
      )
      .subscribe((res: HttpResponse<DailyHealthStatus[]>) => {
        this.onSuccess(res.body, res.headers);
      });
  }

  get medicineList(): FormArray {
    return this.editForm.get('medicines') as FormArray;
  }

  newMedicine(): FormGroup {
    return this.fb.group({
      description: '',
      prescriptionId: this.editForm.get('id'),
      medicineId: undefined,
      price: '',
      amount: 0,
    });
  }

  addMedicines() {
    this.medicineList.push(this.newMedicine());
  }

  removeMedicine(i: number) {
    this.medicineList.removeAt(i);
  }

  private onSuccess(medicines: IMedicine[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.medicines = medicines;
  }

  private sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? ASC : DESC)];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmAction(): void {
    this.updateAction(this.action);
    console.error('aaaa', this.action);
    this.patientManagementService.saveAction(this.action).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }

  private updateAction(action: DoctorAction): void {
    action.id = this.editForm.get(['id'])!.value;
    action.advise = this.editForm.get(['advise'])!.value;
    action.note = this.editForm.get(['notes'])!.value;
    action.dailyHealthStatusId = this.dailyHealthStatus?.id;
    action.medicines = this.editForm.get('medicines')?.value;
  }

  updateMedicines(id: number, index: number): void {
    const medicine = this.medicines?.filter(elm => elm.id === id)[0];
    const price = medicine!.price;
    this.editForm.get('medicines')?.get(String(index))?.patchValue({
      price: price,
    });
    const data = this.editForm.get('medicines')?.value;
    this.action.medicines = data;
    this.updateTotal();
  }

  updateTotal(): void {
    this.total = 0;
    const data = this.editForm.get('medicines')?.value;
    console.error('daaa', data);
    data.filter(elm => (this.total += elm.price! * elm.amount!));
  }
}
