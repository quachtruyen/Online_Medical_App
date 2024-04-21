import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { Observable, of } from 'rxjs';
import { PatientManagementDetailComponent } from './detail/patient-management-detail.component';
import { PatientManagementComponent } from './list/patient-management.component';
import { PatientManagementService } from './service/patient-management.service';
import { IUser, User } from '../../admin/user-management/user-management.model';

@Injectable({ providedIn: 'root' })
export class PatientManagementResolve implements Resolve<IUser> {
  constructor(private service: PatientManagementService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUser> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id);
    }
    return of(new User());
  }
}

export const PatientManagementRoute: Routes = [
  {
    path: '',
    component: PatientManagementComponent,
    data: {
      defaultSort: 'id,asc',
    },
  },
  {
    path: ':id/view',
    component: PatientManagementDetailComponent,
    resolve: {
      user: PatientManagementResolve,
    },
  },
];
