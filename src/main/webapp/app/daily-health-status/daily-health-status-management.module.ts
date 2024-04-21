import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { DailyHealthStatusManagementDetailComponent } from './detail/daily-health-status-management-detail.component';
import { DailyHealthStatusManagementRoute } from './daily-health-status-management.route';
import { NgSelectModule } from '@ng-select/ng-select';
import { DoctorActionDetailComponent } from './doctor-action/detail/doctor-action-detail.component';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(DailyHealthStatusManagementRoute), NgSelectModule],
  declarations: [DoctorActionDetailComponent, DailyHealthStatusManagementDetailComponent],
})
export class DailyHealthStatusManagementModule {}
