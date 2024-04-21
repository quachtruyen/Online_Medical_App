import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { Pagination } from 'app/core/request/request.model';
import { IUser } from 'app/admin/user-management/user-management.model';
import { DoctorAction } from '../doctor-action.model';
import { IMedicine } from '../../../admin/medicine-management/medicine-management.model';

@Injectable({ providedIn: 'root' })
export class PatientManagementService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/doctor/patients');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(user: IUser): Observable<IUser> {
    return this.http.post<IUser>(this.resourceUrl, user);
  }

  update(user: IUser): Observable<IUser> {
    return this.http.put<IUser>(this.resourceUrl, user);
  }

  find(id: number): Observable<IUser> {
    return this.http.get<IUser>(`${this.resourceUrl}/${id}`);
  }

  queryMedicine(req?: Pagination, search?: string): Observable<HttpResponse<IMedicine[]>> {
    if (!search) {
      search = '';
    }
    const options = createRequestOption(req);

    return this.http.get<IMedicine[]>(`/api/medicine/list?search=${search}`, { params: options, observe: 'response' });
  }

  query(req?: Pagination, healthStatus?: string, patientStatus?: string): Observable<HttpResponse<IUser[]>> {
    if (!healthStatus) {
      healthStatus = '';
    }
    if (!patientStatus) {
      patientStatus = '';
    }
    const options = createRequestOption(req);
    return this.http.get<IUser[]>(`${this.resourceUrl}?healthStatus=${healthStatus}&patientStatus=${patientStatus}`, {
      params: options,
      observe: 'response',
    });
  }

  delete(login: string): Observable<{}> {
    return this.http.delete(`${this.resourceUrl}/${login}`);
  }

  saveAction(action?: DoctorAction): Observable<void> {
    return this.http.post<void>('/api/doctor-action', action);
  }

  authorities(): Observable<string[]> {
    return this.http.get<string[]>(this.applicationConfigService.getEndpointFor('api/authorities'));
  }
}
