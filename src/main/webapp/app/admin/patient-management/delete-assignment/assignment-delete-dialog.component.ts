import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { AssignmentService } from '../../user-management/service/assignment.service';

@Component({
  selector: 'jhi-user-mgmt-delete-dialog',
  templateUrl: './assignment-delete-dialog.component.html',
})
export class AssignmentDeleteDialogComponent {
  id?: number;

  constructor(private assignmentService: AssignmentService, private activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.assignmentService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
