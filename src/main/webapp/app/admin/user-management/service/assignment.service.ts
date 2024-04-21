import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { IUser } from '../user-management.model';
import { Assignment } from '../assignment.model';

@Injectable({ providedIn: 'root' })
export class AssignmentService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('/api/assignment');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(assignment: Assignment): Observable<IUser> {
    return this.http.post<IUser>(this.resourceUrl, assignment);
  }

  find(login: string): Observable<IUser> {
    return this.http.get<IUser>(`${this.resourceUrl}/${login}`);
  }

  delete(id: number): Observable<{}> {
    return this.http.delete(`${this.resourceUrl}/${id}`);
  }

  finish(id: number): Observable<{}> {
    return this.http.put(`${this.resourceUrl}/${id}`, null);
  }
}
