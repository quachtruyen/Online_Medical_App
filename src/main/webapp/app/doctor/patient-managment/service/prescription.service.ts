import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { PrescriptionModel } from '../prescription.model';

@Injectable({ providedIn: 'root' })
export class PrescriptionService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/prescription');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  find(id: number): Observable<PrescriptionModel> {
    return this.http.get<PrescriptionModel>(`${this.resourceUrl}/${id}`);
  }
}
