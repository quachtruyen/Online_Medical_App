import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { Observable, of } from 'rxjs';

import { Post, IPost } from './post-management.model';
import { PostManagementService } from './service/post-management.service';
import { PostManagementComponent } from './list/post-management.component';
import { PostManagementDetailComponent } from './detail/post-management-detail.component';
import { PostManagementUpdateComponent } from './update/post-management-update.component';

@Injectable({ providedIn: 'root' })
export class PostManagementResolve implements Resolve<IPost> {
  constructor(private service: PostManagementService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPost> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id);
    }
    return of(new Post());
  }
}

export const postManagementRoute: Routes = [
  {
    path: '',
    component: PostManagementComponent,
    data: {
      defaultSort: 'id,asc',
    },
  },
  {
    path: ':id/view',
    component: PostManagementDetailComponent,
    resolve: {
      post: PostManagementResolve,
    },
  },
  {
    path: 'new',
    component: PostManagementUpdateComponent,
    resolve: {
      post: PostManagementResolve,
    },
  },
  {
    path: ':id/edit',
    component: PostManagementUpdateComponent,
    resolve: {
      post: PostManagementResolve,
    },
  },
];
