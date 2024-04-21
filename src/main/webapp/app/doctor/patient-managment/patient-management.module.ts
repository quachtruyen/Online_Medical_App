import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { PatientManagementComponent } from './list/patient-management.component';
import { PatientManagementDetailComponent } from './detail/patient-management-detail.component';
import { PatientManagementRoute } from './patient-management.route';
import { DoctorActionDialogComponent } from './doctor-action/doctor-action-dialog.component';
import { NgSelectModule } from '@ng-select/ng-select';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(PatientManagementRoute), NgSelectModule],
  declarations: [PatientManagementComponent, PatientManagementDetailComponent, DoctorActionDialogComponent],
  entryComponents: [DoctorActionDialogComponent],
})
export class PatientManagementModule {}
