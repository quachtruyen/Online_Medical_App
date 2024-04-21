import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { Pagination } from 'app/core/request/request.model';
import { IUser } from 'app/admin/user-management/user-management.model';
import { DailyHealthStatus } from '../../doctor/patient-managment/daily-health-status.model';

@Injectable({ providedIn: 'root' })
export class DoctorActionService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/doctor-action');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(user: IUser): Observable<IUser> {
    return this.http.post<IUser>(this.resourceUrl, user);
  }

  find(id: number): Observable<DailyHealthStatus> {
    return this.http.get<DailyHealthStatus>(`${this.resourceUrl}/${id}`);
  }

  findByDailyHealthStatusId(healthStatusId?: number): Observable<DailyHealthStatus> {
    return this.http.get<DailyHealthStatus>(`${this.resourceUrl}/by-health-status/` + String(healthStatusId));
  }

  query(req?: Pagination, healthStatusId?: number): Observable<HttpResponse<DailyHealthStatus[]>> {
    const options = createRequestOption(req);
    return this.http.get<DailyHealthStatus[]>(`${this.resourceUrl}/by-health-status/` + String(healthStatusId), {
      params: options,
      observe: 'response',
    });
  }
}
