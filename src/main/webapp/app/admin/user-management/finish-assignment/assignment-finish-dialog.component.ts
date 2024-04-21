import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { AssignmentService } from '../service/assignment.service';
import { faArchive } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'jhi-assignment-mgmt-finish-dialog',
  templateUrl: './assignment-finish-dialog.component.html',
})
export class AssignmentFinishDialogComponent {
  id?: number;
  accept = faArchive;

  constructor(private assignmentService: AssignmentService, private activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmFinish(id: number): void {
    this.assignmentService.finish(id).subscribe(() => {
      this.activeModal.close('finished');
    });
  }
}
