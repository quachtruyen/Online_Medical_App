import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { Medicine } from '../medicine-management.model';
import { MedicineManagementService } from '../service/medicine-management.service';

@Component({
  selector: 'jhi-medicine-mgmt-delete-dialog',
  templateUrl: './medicine-management-delete-dialog.component.html',
})
export class MedicineManagementDeleteDialogComponent {
  medicine?: Medicine;

  constructor(private medicineService: MedicineManagementService, private activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.medicineService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
