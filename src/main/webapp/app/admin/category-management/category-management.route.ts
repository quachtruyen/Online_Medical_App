import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { Observable, of } from 'rxjs';

import { Category, ICategory } from './category-management.model';
import { CategoryManagementService } from './service/category-management.service';
import { CategoryManagementComponent } from './list/category-management.component';
import { CategoryManagementDetailComponent } from './detail/category-management-detail.component';
import { CategoryManagementUpdateComponent } from './update/category-management-update.component';

@Injectable({ providedIn: 'root' })
export class CategoryManagementResolve implements Resolve<ICategory> {
  constructor(private service: CategoryManagementService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICategory> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id);
    }
    return of(new Category());
  }
}

export const categoryManagementRoute: Routes = [
  {
    path: '',
    component: CategoryManagementComponent,
    data: {
      defaultSort: 'id,asc',
    },
  },
  {
    path: ':id/view',
    component: CategoryManagementDetailComponent,
    resolve: {
      category: CategoryManagementResolve,
    },
  },
  {
    path: 'new',
    component: CategoryManagementUpdateComponent,
    resolve: {
      category: CategoryManagementResolve,
    },
  },
  {
    path: ':id/edit',
    component: CategoryManagementUpdateComponent,
    resolve: {
      category: CategoryManagementResolve,
    },
  },
];
