import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { Observable, of } from 'rxjs';

import { PatientManagementComponent } from './list/patient-management.component';
import { PatientManagementDetailComponent } from './detail/patient-management-detail.component';
import { PatientManagementUpdateComponent } from './update/patient-management-update.component';
import { UserManagementService } from '../user-management/service/user-management.service';
import { Doctor } from '../user-management/doctor.model';
import { PatientManagementService } from '../../doctor/patient-managment/service/patient-management.service';

@Injectable({ providedIn: 'root' })
export class UserManagementResolve implements Resolve<Doctor> {
  constructor(private service: UserManagementService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<Doctor> {
    const id = route.params['login'];
    if (id) {
      return this.service.find(id);
    }
    return of(new Doctor());
  }
}

export const patientManagementRoute: Routes = [
  {
    path: '',
    component: PatientManagementComponent,
    data: {
      defaultSort: 'id,asc',
    },
  },
  {
    path: ':login/view',
    component: PatientManagementDetailComponent,
    resolve: {
      user: UserManagementResolve,
    },
  },
  {
    path: 'new',
    component: PatientManagementUpdateComponent,
    resolve: {
      user: UserManagementResolve,
    },
  },
  {
    path: ':login/edit',
    component: PatientManagementUpdateComponent,
    resolve: {
      user: UserManagementResolve,
    },
  },
];
