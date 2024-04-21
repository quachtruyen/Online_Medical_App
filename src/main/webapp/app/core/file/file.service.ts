import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { StateStorageService } from 'app/core/auth/state-storage.service';
import { ApplicationConfigService } from '../config/application-config.service';

@Injectable({ providedIn: 'root' })
export class FileService {
  constructor(
    private http: HttpClient,
    private stateStorageService: StateStorageService,
    private applicationConfigService: ApplicationConfigService
  ) {}

  private resourceUrl = this.applicationConfigService.getEndpointFor('api/file');

  public uploadFile(formData: FormData, id: number, type: string): Observable<void> {
    return this.http.post<void>(`${this.resourceUrl}/upload?id=${id}&type=${type}`, formData);
  }
}
