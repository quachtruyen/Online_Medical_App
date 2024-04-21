import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { Observable, of } from 'rxjs';
import { DailyHealthStatusManagementDetailComponent } from './detail/daily-health-status-management-detail.component';
import { DailyHealthStatus } from '../doctor/patient-managment/daily-health-status.model';
import { DailyHealthStatusService } from './daily-health-status-management.service';
import { DoctorActionDetailComponent } from './doctor-action/detail/doctor-action-detail.component';
import { DoctorAction } from '../doctor/patient-managment/doctor-action.model';
import { DoctorActionService } from './doctor-action/doctor-action.service';

@Injectable({ providedIn: 'root' })
export class DailyHealthStatusManagementResolve implements Resolve<DailyHealthStatus> {
  constructor(private service: DailyHealthStatusService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<DailyHealthStatus> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id);
    }
    return of(new DailyHealthStatus());
  }
}

export class DoctorActionResolve implements Resolve<DoctorAction> {
  constructor(private service: DoctorActionService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<DoctorAction> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id);
    }
    return of(new DoctorAction());
  }
}

export const DailyHealthStatusManagementRoute: Routes = [
  {
    path: ':id/view',
    component: DailyHealthStatusManagementDetailComponent,
    resolve: {
      dailyHealthStatus: DailyHealthStatusManagementResolve,
    },
  },
  {
    path: 'doctor-action/:id/view',
    component: DoctorActionDetailComponent,
    resolve: {
      doctorAction: DoctorActionResolve,
    },
  },
];
