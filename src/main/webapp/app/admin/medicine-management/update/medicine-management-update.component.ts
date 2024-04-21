import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { LANGUAGES } from 'app/config/language.constants';
import { Medicine } from '../medicine-management.model';
import { MedicineManagementService } from '../service/medicine-management.service';
import { FileService } from '../../../core/file/file.service';
import { AuthServerProvider } from '../../../core/auth/auth-jwt.service';
import { faUpload } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'jhi-medicine-mgmt-update',
  templateUrl: './medicine-management-update.component.html',
})
export class MedicineManagementUpdateComponent implements OnInit {
  medicine!: Medicine;
  languages = LANGUAGES;
  authorities: string[] = [];
  fileName = '';
  isSaving = false;
  formData!: FormData;
  token!: String;

  editForm = this.fb.group({
    id: [],
    name: ['', [Validators.required, Validators.maxLength(255)]],
    origin: ['', [Validators.required, Validators.maxLength(255)]],
    element: ['', [Validators.required, Validators.maxLength(255)]],
    uses: ['', [Validators.required, Validators.maxLength(255)]],
    price: ['', [Validators.required, Validators.maxLength(255)]],
  });
  faUpload = faUpload;

  constructor(
    private medicineService: MedicineManagementService,
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private fileService: FileService,
    private auth: AuthServerProvider
  ) {}

  ngOnInit(): void {
    this.token = this.auth.getToken();
    this.route.data.subscribe(({ medicine }) => {
      if (medicine) {
        this.medicine = medicine;

        this.updateForm(medicine);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    this.updateMedicine(this.medicine);
    if (this.medicine.id !== undefined) {
      this.medicineService.update(this.medicine).subscribe(
        medicine => this.onSaveSuccess(medicine.id!),
        () => this.onSaveError()
      );
    } else {
      this.medicineService.create(this.medicine).subscribe(
        medicine => this.onSaveSuccess(medicine.id!),
        () => this.onSaveError()
      );
    }
  }

  private updateForm(medicine: Medicine): void {
    this.editForm.patchValue({
      id: medicine.id,
      name: medicine.name,
      origin: medicine.origin,
      element: medicine.element,
      uses: medicine.uses,
      price: medicine.price,
    });
  }

  private updateMedicine(medicine: Medicine): void {
    medicine.name = this.editForm.get(['name'])!.value;
    medicine.origin = this.editForm.get(['origin'])!.value;
    medicine.element = this.editForm.get(['element'])!.value;
    medicine.uses = this.editForm.get(['uses'])!.value;
    medicine.price = this.editForm.get(['price'])!.value;
  }

  private onSaveSuccess(id: number): void {
    if (this.formData && this.formData.get('file')) {
      this.fileService.uploadFile(this.formData, id, 'medicine').subscribe(() => {
        console.error('success');
        this.isSaving = false;
        this.previousState();
      });
    } else {
      this.isSaving = false;
      this.previousState();
    }
  }

  onFileSelected(event) {
    const file: File = event.target.files[0];
    const formData = new FormData();

    if (file) {
      this.fileName = file.name;
      formData.append('file', file);
    } else {
      this.fileName = '';
      formData.delete('file');
    }
    this.formData = formData;
  }

  private onSaveError(): void {
    this.isSaving = false;
  }
}
