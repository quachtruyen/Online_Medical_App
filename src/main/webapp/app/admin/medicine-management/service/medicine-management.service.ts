import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { Pagination } from 'app/core/request/request.model';
import { IMedicine } from '../medicine-management.model';

@Injectable({ providedIn: 'root' })
export class MedicineManagementService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/medicine');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(medicine: IMedicine): Observable<IMedicine> {
    return this.http.post<IMedicine>(this.resourceUrl, medicine);
  }

  update(medicine: IMedicine): Observable<IMedicine> {
    return this.http.put<IMedicine>(this.resourceUrl, medicine);
  }

  find(id: number): Observable<IMedicine> {
    return this.http.get<IMedicine>(`${this.resourceUrl}/${id}`);
  }

  queryUser(req?: Pagination, search?: string): Observable<HttpResponse<IMedicine[]>> {
    if (!search) {
      search = '';
    }
    const options = createRequestOption(req);
    return this.http.get<IMedicine[]>(`/api/medicine/list?search=${search}`, { params: options, observe: 'response' });
  }

  query(req?: Pagination, search?: string): Observable<HttpResponse<IMedicine[]>> {
    if (!search) {
      search = '';
    }
    const options = createRequestOption(req);
    return this.http.get<IMedicine[]>(`/api/medicine?search=${search}`, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<{}> {
    return this.http.delete(`${this.resourceUrl}/${id}`);
  }

  authorities(): Observable<string[]> {
    return this.http.get<string[]>(this.applicationConfigService.getEndpointFor('api/authorities'));
  }
}
