import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { Category } from '../category-management.model';
import { CategoryManagementService } from '../service/category-management.service';

@Component({
  selector: 'jhi-category-mgmt-delete-dialog',
  templateUrl: './category-management-delete-dialog.component.html',
})
export class CategoryManagementDeleteDialogComponent {
  category?: Category;

  constructor(private categoryService: CategoryManagementService, private activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.categoryService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
