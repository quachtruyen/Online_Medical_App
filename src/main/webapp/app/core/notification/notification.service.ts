import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { TranslateService } from '@ngx-translate/core';
import { SessionStorageService } from 'ngx-webstorage';
import { Observable, ReplaySubject, of, Subscription, Subject } from 'rxjs';
import { shareReplay, tap, catchError } from 'rxjs/operators';

import { StateStorageService } from 'app/core/auth/state-storage.service';
import { ApplicationConfigService } from '../config/application-config.service';
import { Account } from 'app/core/auth/account.model';
import { TrackerService } from '../tracker/tracker.service';
import { NotificationModel } from './notification.model';
import { Pagination } from '../request/request.model';
import { IMedicine } from '../../admin/medicine-management/medicine-management.model';
import { createRequestOption } from '../request/request-util';
import * as Stomp from 'webstomp-client';
import { TrackerActivity } from '../tracker/tracker-activity.model';

@Injectable({ providedIn: 'root' })
export class NotificationService {
  private connectionSubscription: Subscription | null = null;
  private stompSubscription: Stomp.Subscription | null = null;
  private stompClient: Stomp.Client | null = null;
  private routerSubscription: Subscription | null = null;
  private connectionSubject: ReplaySubject<void> = new ReplaySubject(1);
  private listenerSubject: Subject<NotificationModel> = new Subject();

  constructor(
    private translateService: TranslateService,
    private sessionStorageService: SessionStorageService,
    private http: HttpClient,
    private trackerService: TrackerService,
    private stateStorageService: StateStorageService,
    private router: Router,
    private applicationConfigService: ApplicationConfigService
  ) {}

  private resourceUrl = this.applicationConfigService.getEndpointFor('api/notification');

  query(req?: Pagination, search?: string, status?: string): Observable<HttpResponse<NotificationModel[]>> {
    if (!search) {
      search = '';
    }
    if (!status) {
      status = '';
    }
    const options = createRequestOption(req);
    return this.http.get<NotificationModel[]>(`${this.resourceUrl}?search=${search}&status=${status}`, {
      params: options,
      observe: 'response',
    });
  }

  subscribe(login: string): void {
    if (this.connectionSubscription) {
      return;
    }

    this.connectionSubscription = this.connectionSubject.subscribe(() => {
      if (this.stompClient) {
        this.stompSubscription = this.stompClient.subscribe('/topic/' + login + '/notification', (data: Stomp.Message) => {
          console.error('iiiiii', data);
          this.listenerSubject.next(JSON.parse(data.body));
        });
      }
    });
  }

  receive(): Subject<NotificationModel> {
    return this.listenerSubject;
  }

  public markReadOne(id: number): Observable<void> {
    return this.http.put<void>(`${this.resourceUrl}/readOne?id=${id}`, null);
  }

  public markReadAll(): Observable<void> {
    return this.http.put<void>(`${this.resourceUrl}/readAll`, null);
  }

  public getTotalUnread(): Observable<number> {
    return this.http.get<number>(`${this.resourceUrl}/totalUnread`);
  }
}
