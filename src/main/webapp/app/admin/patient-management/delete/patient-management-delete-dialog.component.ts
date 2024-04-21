import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { UserManagementService } from '../../user-management/service/user-management.service';
import { User } from '../../user-management/user-management.model';

@Component({
  selector: 'jhi-user-mgmt-delete-dialog',
  templateUrl: './patient-management-delete-dialog.component.html',
})
export class PatientManagementDeleteDialogComponent {
  user?: User;

  constructor(private userService: UserManagementService, private activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(login: string): void {
    this.userService.delete(login).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
