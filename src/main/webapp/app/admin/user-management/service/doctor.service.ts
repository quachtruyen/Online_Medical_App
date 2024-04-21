import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { Pagination } from 'app/core/request/request.model';
import { IUser, User } from '../user-management.model';
import { Doctor } from '../doctor.model';

@Injectable({ providedIn: 'root' })
export class DoctorService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/doctor');

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

  getAssignToMe(): Observable<User> {
    return this.http.get<User>(`${this.resourceUrl}/detail/assignedToMe`);
  }

  query(req?: Pagination, search?: string): Observable<HttpResponse<IUser[]>> {
    const options = createRequestOption(req);
    if (!search) {
      search = '';
    }
    return this.http.get<IUser[]>(`${this.resourceUrl}/list?search=` + String(search), { params: options, observe: 'response' });
  }

  delete(login: string): Observable<{}> {
    return this.http.delete(`${this.resourceUrl}/${login}`);
  }

  authorities(): Observable<string[]> {
    return this.http.get<string[]>(this.applicationConfigService.getEndpointFor('api/authorities'));
  }
}
