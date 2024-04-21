import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { FormBuilder, Validators } from '@angular/forms';
import { DailyHealthStatus } from '../../doctor/patient-managment/daily-health-status.model';
import { DailyHealthStatusService } from '../daily-health-status-management.service';
import { faUpload } from '@fortawesome/free-solid-svg-icons';
import { FileService } from '../../core/file/file.service';

@Component({
  selector: 'jhi-update-daily-health-status-dialog-dialog',
  templateUrl: './update-daily-health-status-dialog.component.html',
})
export class UpdateDailyHealthDialogComponent {
  dailyHealthStatus: DailyHealthStatus = {};
  fileName = '';
  formData!: FormData;
  editForm = this.fb.group({
    healthStatus: ['', [Validators.required]],
    symptoms: ['', [Validators.required]],
    notes: ['', [Validators.required]],
  });
  statuses = [
    { status: 'normal', content: 'Normal' },
    { status: 'light', content: 'Light' },
    { status: 'emergency', content: 'Emergency' },
    { status: 'critical', content: 'Critical' },
  ];
  faUpload = faUpload;
  constructor(
    private fb: FormBuilder,
    private activeModal: NgbActiveModal,
    private dailyHealthStatusService: DailyHealthStatusService,
    private fileService: FileService
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmHealthStatus(): void {
    this.updateHealthStatus(this.dailyHealthStatus);
    this.dailyHealthStatusService.create(this.dailyHealthStatus).subscribe(dailyHealthStatus => {
      if (this.formData && this.formData.get('file')) {
        this.fileService.uploadFile(this.formData, dailyHealthStatus.id!, 'symptom').subscribe(() => {
          this.activeModal.close();
        });
      } else {
        this.activeModal.close();
      }
    });
  }

  updateHealthStatus(dailyHealthStatus: DailyHealthStatus): void {
    dailyHealthStatus.healthStatus = this.editForm.get(['healthStatus'])!.value;
    dailyHealthStatus.notes = this.editForm.get(['notes'])!.value;
    dailyHealthStatus.symptoms = this.editForm.get(['symptoms'])!.value;
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
}
