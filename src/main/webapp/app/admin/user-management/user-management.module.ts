import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { UserManagementComponent } from './list/user-management.component';
import { UserManagementDetailComponent } from './detail/user-management-detail.component';
import { UserManagementUpdateComponent } from './update/user-management-update.component';
import { UserManagementDeleteDialogComponent } from './delete/user-management-delete-dialog.component';
import { userManagementRoute } from './user-management.route';
import { AssginmentDialogComponent } from './assignment/assignment-dialog.component';
import { AssignmentDeleteDialogComponent } from './delete-assignment/assignment-delete-dialog.component';
import { AssignmentFinishDialogComponent } from './finish-assignment/assignment-finish-dialog.component';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(userManagementRoute)],
  declarations: [
    UserManagementComponent,
    UserManagementDetailComponent,
    UserManagementUpdateComponent,
    UserManagementDeleteDialogComponent,
    AssginmentDialogComponent,
    AssignmentDeleteDialogComponent,
    AssignmentFinishDialogComponent,
  ],
  entryComponents: [
    UserManagementDeleteDialogComponent,
    AssginmentDialogComponent,
    AssignmentDeleteDialogComponent,
    AssignmentFinishDialogComponent,
  ],
})
export class UserManagementModule {}
