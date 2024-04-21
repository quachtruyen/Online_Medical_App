import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { Pagination } from 'app/core/request/request.model';
import { ICategory } from '../../admin/category-management/category-management.model';

@Injectable({ providedIn: 'root' })
export class CategoryService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/category');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  query(req?: Pagination, search?: string): Observable<HttpResponse<ICategory[]>> {
    if (!search) {
      search = '';
    }
    const options = createRequestOption(req);
    return this.http.get<ICategory[]>(`${this.resourceUrl}?search=${search}`, { params: options, observe: 'response' });
  }
}
