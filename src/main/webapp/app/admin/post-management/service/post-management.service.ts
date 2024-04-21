import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { Pagination } from 'app/core/request/request.model';
import { IPost } from '../post-management.model';

@Injectable({ providedIn: 'root' })
export class PostManagementService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/post');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(post: IPost): Observable<IPost> {
    return this.http.post<IPost>(this.resourceUrl, post);
  }

  update(post: IPost): Observable<IPost> {
    return this.http.put<IPost>(this.resourceUrl, post);
  }

  publish(id: number, published: boolean): Observable<void> {
    return this.http.put<void>(this.resourceUrl + '/publish', { id: id, published: published });
  }

  find(id: number): Observable<IPost> {
    return this.http.get<IPost>(`${this.resourceUrl}/${id}`);
  }

  query(req?: Pagination, search?: string): Observable<HttpResponse<IPost[]>> {
    if (!search) {
      search = '';
    }
    const options = createRequestOption(req);
    return this.http.get<IPost[]>(`${this.resourceUrl}?search=${search}`, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<{}> {
    return this.http.delete(`${this.resourceUrl}/${id}`);
  }

  authorities(): Observable<string[]> {
    return this.http.get<string[]>(this.applicationConfigService.getEndpointFor('api/authorities'));
  }
}
