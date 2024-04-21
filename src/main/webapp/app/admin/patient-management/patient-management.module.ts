import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { PatientManagementComponent } from './list/patient-management.component';
import { PatientManagementDetailComponent } from './detail/patient-management-detail.component';
import { PatientManagementUpdateComponent } from './update/patient-management-update.component';
import { PatientManagementDeleteDialogComponent } from './delete/patient-management-delete-dialog.component';
import { patientManagementRoute } from './patient-management.route';
import { AssginmentDialogComponent } from './assignment/assignment-dialog.component';
import { AssignmentDeleteDialogComponent } from './delete-assignment/assignment-delete-dialog.component';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(patientManagementRoute)],
  declarations: [
    PatientManagementComponent,
    PatientManagementDetailComponent,
    PatientManagementUpdateComponent,
    PatientManagementDeleteDialogComponent,
    AssginmentDialogComponent,
    AssignmentDeleteDialogComponent,
  ],
  entryComponents: [PatientManagementDeleteDialogComponent, AssginmentDialogComponent, AssignmentDeleteDialogComponent],
})
export class PatientManagementModule {}
