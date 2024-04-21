import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { Pagination } from 'app/core/request/request.model';
import { IUser } from '../user-management/user-management.model';

@Injectable({ providedIn: 'root' })
export class PatientService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/admin/patients');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(user: IUser): Observable<IUser> {
    return this.http.post<IUser>(this.resourceUrl, user);
  }

  update(user: IUser): Observable<IUser> {
    return this.http.put<IUser>(this.resourceUrl, user);
  }

  find(login: string): Observable<IUser> {
    return this.http.get<IUser>(`${this.resourceUrl}/${login}`);
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

  authorities(): Observable<string[]> {
    return this.http.get<string[]>(this.applicationConfigService.getEndpointFor('api/authorities'));
  }

  healtStatsus() {
    return this.http.get<string[]>(this.applicationConfigService.getEndpointFor('api/health-statuses'));
  }
}
