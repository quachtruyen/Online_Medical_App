import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { Pagination } from 'app/core/request/request.model';
import { ICategory } from '../category-management.model';

@Injectable({ providedIn: 'root' })
export class CategoryManagementService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/category');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(category: ICategory): Observable<ICategory> {
    return this.http.post<ICategory>(this.resourceUrl, category);
  }

  update(category: ICategory): Observable<ICategory> {
    return this.http.put<ICategory>(this.resourceUrl, category);
  }

  find(id: number): Observable<ICategory> {
    return this.http.get<ICategory>(`${this.resourceUrl}/${id}`);
  }

  query(req?: Pagination, search?: string): Observable<HttpResponse<ICategory[]>> {
    if (!search) {
      search = '';
    }
    const options = createRequestOption(req);
    return this.http.get<ICategory[]>(`${this.resourceUrl}?search=${search}`, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<{}> {
    return this.http.delete(`${this.resourceUrl}/${id}`);
  }

  authorities(): Observable<string[]> {
    return this.http.get<string[]>(this.applicationConfigService.getEndpointFor('api/authorities'));
  }
}
