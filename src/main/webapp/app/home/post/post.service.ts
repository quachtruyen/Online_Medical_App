import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { Pagination } from 'app/core/request/request.model';
import { IPost } from '../../admin/post-management/post-management.model';

@Injectable({ providedIn: 'root' })
export class PostService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/post');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  find(id: number): Observable<IPost> {
    return this.http.get<IPost>(`${this.resourceUrl}/${id}`);
  }

  query(req?: Pagination, search?: string): Observable<HttpResponse<IPost[]>> {
    if (!search) {
      search = '';
    }
    const options = createRequestOption(req);
    return this.http.get<IPost[]>(`${this.resourceUrl}/by-user?search=${search}`, { params: options, observe: 'response' });
  }

  queryByCategory(req?: Pagination, categoryId?: number): Observable<HttpResponse<IPost[]>> {
    if (!categoryId) {
      categoryId = 1;
    }
    const options = createRequestOption(req);
    return this.http.get<IPost[]>(`${this.resourceUrl}/by-category?categoryId=${categoryId}`, { params: options, observe: 'response' });
  }
}
