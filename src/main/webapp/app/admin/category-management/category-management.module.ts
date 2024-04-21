import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { CategoryManagementComponent } from './list/category-management.component';
import { CategoryManagementDetailComponent } from './detail/category-management-detail.component';
import { CategoryManagementUpdateComponent } from './update/category-management-update.component';
import { CategoryManagementDeleteDialogComponent } from './delete/category-management-delete-dialog.component';
import { categoryManagementRoute } from './category-management.route';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(categoryManagementRoute)],
  declarations: [
    CategoryManagementComponent,
    CategoryManagementDetailComponent,
    CategoryManagementUpdateComponent,
    CategoryManagementDeleteDialogComponent,
  ],
  entryComponents: [CategoryManagementDeleteDialogComponent],
})
export class CategoryManagementModule {}
