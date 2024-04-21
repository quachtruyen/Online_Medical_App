import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { Post } from '../post-management.model';
import { PostManagementService } from '../service/post-management.service';

@Component({
  selector: 'jhi-user-mgmt-delete-dialog',
  templateUrl: './post-management-delete-dialog.component.html',
})
export class PostManagementDeleteDialogComponent {
  post?: Post;

  constructor(private postService: PostManagementService, private activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.postService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
