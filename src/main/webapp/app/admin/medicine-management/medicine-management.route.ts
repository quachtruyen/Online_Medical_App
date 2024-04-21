import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { Observable, of } from 'rxjs';

import { Medicine, IMedicine } from './medicine-management.model';
import { MedicineManagementService } from './service/medicine-management.service';
import { MedicineManagementComponent } from './list/medicine-management.component';
import { MedicineManagementDetailComponent } from './detail/medicine-management-detail.component';
import { MedicineManagementUpdateComponent } from './update/medicine-management-update.component';

@Injectable({ providedIn: 'root' })
export class MedicineManagementResolve implements Resolve<IMedicine> {
  constructor(private service: MedicineManagementService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMedicine> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id);
    }
    return of(new Medicine());
  }
}

export const medicineManagementRoute: Routes = [
  {
    path: '',
    component: MedicineManagementComponent,
    data: {
      defaultSort: 'id,asc',
    },
  },
  {
    path: ':id/view',
    component: MedicineManagementDetailComponent,
    resolve: {
      medicine: MedicineManagementResolve,
    },
  },
  {
    path: 'new',
    component: MedicineManagementUpdateComponent,
    resolve: {
      medicine: MedicineManagementResolve,
    },
  },
  {
    path: ':id/edit',
    component: MedicineManagementUpdateComponent,
    resolve: {
      medicine: MedicineManagementResolve,
    },
  },
];
