import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { Pagination } from 'app/core/request/request.model';
import { IUser } from 'app/admin/user-management/user-management.model';
import { DailyHealthStatus } from '../doctor/patient-managment/daily-health-status.model';

@Injectable({ providedIn: 'root' })
export class DailyHealthStatusService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/daily-health-status');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(dailyHealthStatus: DailyHealthStatus): Observable<DailyHealthStatus> {
    return this.http.post<DailyHealthStatus>(this.resourceUrl, dailyHealthStatus);
  }

  update(user: IUser): Observable<IUser> {
    return this.http.put<IUser>(this.resourceUrl, user);
  }

  find(id: number): Observable<DailyHealthStatus> {
    return this.http.get<DailyHealthStatus>(`${this.resourceUrl}/detail/${id}`);
  }

  checkIsUpdatedToday(): Observable<boolean> {
    return this.http.get<boolean>(`${this.resourceUrl}/check-update-today`);
  }

  query(req?: Pagination, patientId?: number): Observable<HttpResponse<DailyHealthStatus[]>> {
    const options = createRequestOption(req);
    return this.http.get<DailyHealthStatus[]>(`${this.resourceUrl}/` + String(patientId), { params: options, observe: 'response' });
  }

  delete(login: string): Observable<{}> {
    return this.http.delete(`${this.resourceUrl}/${login}`);
  }
}
