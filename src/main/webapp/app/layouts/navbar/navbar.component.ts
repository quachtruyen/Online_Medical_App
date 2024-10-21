import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { SessionStorageService } from 'ngx-webstorage';

import { VERSION } from 'app/app.constants';
import { LANGUAGES } from 'app/config/language.constants';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { LoginService } from 'app/login/login.service';
import { ProfileService } from 'app/layouts/profiles/profile.service';
import { NotificationService } from '../../core/notification/notification.service';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ASC, DESC, ITEMS_PER_PAGE } from '../../config/pagination.constants';
import { NotificationModel } from '../../core/notification/notification.model';
import { Subscription } from 'rxjs';
import { TrackerService } from '../../core/tracker/tracker.service';
import { UpdateDailyHealthDialogComponent } from '../../daily-health-status/update-daily-health-status-dialog/update-daily-health-dialog.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { faAddressBook, faBacon } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'jhi-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent implements OnInit {
  inProduction?: boolean;
  isNavbarCollapsed = true;
  languages = LANGUAGES;
  fabook = faAddressBook;
  faBacon = faBacon;
  openAPIEnabled?: boolean;
  version = '';
  account: Account | null = null;
  search?: string;
  totalItems = 0;
  itemsPerPage = 1000;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  notifications: NotificationModel[] | null = null;
  currentStatus = '';
  totalUnread = 0;
  subscription?: Subscription;

  constructor(
    private loginService: LoginService,
    private translateService: TranslateService,
    private sessionStorageService: SessionStorageService,
    private accountService: AccountService,
    private profileService: ProfileService,
    private router: Router,
    private notificationService: NotificationService,
    private trackerService: TrackerService,
    private modalService: NgbModal
  ) {
    if (VERSION) {
      this.version = VERSION.toLowerCase().startsWith('v') ? VERSION : 'v' + VERSION;
    }
  }

  ngOnInit(): void {
    this.profileService.getProfileInfo().subscribe(profileInfo => {
      this.inProduction = profileInfo.inProduction;
      this.openAPIEnabled = profileInfo.openAPIEnabled;
    });
    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;

      this.page = 1;
      this.predicate = 'createdAt';
      this.loadNotification();
      this.getTotalUnread();
      setTimeout(() => {
        this.trackerService.subscribe1(this.account!.login);
        this.subscription = this.trackerService.receive1().subscribe((notification: NotificationModel) => {
          this.loadNotificationFromWebsocket(notification);
        });
      }, 3000);
    });
  }

  changeLanguage(languageKey: string): void {
    this.sessionStorageService.store('locale', languageKey);
    this.translateService.use(languageKey);
  }

  collapseNavbar(): void {
    this.isNavbarCollapsed = true;
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  logout(): void {
    this.collapseNavbar();
    this.loginService.logout();
    this.router.navigate(['']);
  }

  toggleNavbar(): void {
    this.isNavbarCollapsed = !this.isNavbarCollapsed;
  }

  private loadNotification() {
    this.notificationService
      .query({ page: this.page - 1, size: this.itemsPerPage, sort: this.sort() }, this.search, this.currentStatus)
      .subscribe((res: HttpResponse<NotificationModel[]>) => {
        this.onSuccess(res.body, res.headers);
      });
  }

  private onSuccess(medicines: NotificationModel[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.notifications = medicines;
  }

  private sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? ASC : DESC)];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  public changeStatus(status: string) {
    this.currentStatus = status;
    this.loadNotification();
  }

  // eslint-disable-next-line @typescript-eslint/no-empty-function
  // searchStatus = false;
  // public searchGroupChat() {
  //   this.searchStatus = !this.searchStatus;
  // }

  public seeAll() {
    this.notificationService.markReadAll().subscribe(() => {
      this.loadNotification();
      this.getTotalUnread();
    });
  }

  public getTotalUnread() {
    this.notificationService.getTotalUnread().subscribe(total => {
      this.totalUnread = total;
    });
  }

  public seeOne(notification: NotificationModel) {
    if (notification.status === 'unread') {
      this.notificationService.markReadOne(notification.id).subscribe(() => {
        this.loadNotification();
        this.getTotalUnread();
      });
    }
    if (notification.url) {
      this.router.navigate([notification.url]);
    }
  }

  private loadNotificationFromWebsocket(notification: NotificationModel) {
    this.notifications?.unshift(notification);
    this.totalUnread++;
    this.totalItems++;
  }

  public updateHealthStatus() {
    if (this.account?.authorities.includes('ROLE_PATIENT')) {
      this.modalService.open(UpdateDailyHealthDialogComponent, { size: 'lg', backdrop: 'static' });
      // unsubscribe not needed because closed completes on modal close
    }
  }
}
