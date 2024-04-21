import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'app/admin/user-management/user-management.model';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ASC, DESC, ITEMS_PER_PAGE } from '../../../config/pagination.constants';
import { DailyHealthStatus } from '../daily-health-status.model';
import { DailyHealthStatusService } from '../../../daily-health-status/daily-health-status-management.service';
import { DoctorActionDialogComponent } from '../doctor-action/doctor-action-dialog.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AssginmentDialogComponent } from 'app/admin/user-management/assignment/assignment-dialog.component';
import { AssignmentDeleteDialogComponent } from 'app/admin/user-management/delete-assignment/assignment-delete-dialog.component';
import { faAsterisk } from '@fortawesome/free-solid-svg-icons';
import { AssignmentFinishDialogComponent } from '../../../admin/user-management/finish-assignment/assignment-finish-dialog.component';
import { AuthServerProvider } from '../../../core/auth/auth-jwt.service';

@Component({
  selector: 'jhi-patient-mgmt-detail',
  templateUrl: './patient-management-detail.component.html',
})
export class PatientManagementDetailComponent implements OnInit {
  user: User | null = null;
  dailyHealthStatusList: DailyHealthStatus[] | null = [];
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  window = faAsterisk;
  token = '';
  constructor(
    private route: ActivatedRoute,
    private dailyHealthStatusService: DailyHealthStatusService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private modalService: NgbModal,
    private auth: AuthServerProvider
  ) {}

  ngOnInit(): void {
    this.token = this.auth.getToken();
    this.route.data.subscribe(({ user }) => {
      this.user = user;
      this.page = 1;
      this.predicate = 'id';
      this.loadAllDailyHealthStatus(user.id);
    });
  }

  transition(): void {
    this.router.navigate(['./'], {
      relativeTo: this.activatedRoute.parent,
      queryParams: {
        page: this.page,
        sort: this.predicate + ',' + (this.ascending ? ASC : DESC),
      },
    });
  }

  trackIdentity(index: number, item: User): number {
    return item.id!;
  }

  public loadAllDailyHealthStatus(patientId: number | undefined) {
    this.dailyHealthStatusService
      .query(
        {
          page: this.page - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
        },
        patientId
      )
      .subscribe((res: HttpResponse<DailyHealthStatus[]>) => {
        this.onSuccess(res.body, res.headers);
      });
  }

  private sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? ASC : DESC)];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  private onSuccess(dailyHealthStatusList: DailyHealthStatus[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.dailyHealthStatusList = dailyHealthStatusList;
    console.error(this.dailyHealthStatusList);
  }

  openAction(dailyHealthStatus?: DailyHealthStatus) {
    console.error('sss', dailyHealthStatus);
    const modalRef = this.modalService.open(DoctorActionDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.dailyHealthStatus = dailyHealthStatus;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAllDailyHealthStatus(this.user?.id);
      }
    });
  }

  back(): void {
    window.history.back();
  }

  assignUser(user: User): void {
    const modalRef = this.modalService.open(AssginmentDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.user = user;
    // unsubscribe not needed because closed completes on modal close
  }

  deleteAssignment(id: number) {
    const modalRef = this.modalService.open(AssignmentDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.id = id;
  }

  confirmFinish(id: number): void {
    const modalRef = this.modalService.open(AssignmentFinishDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.id = id;
    modalRef.closed.subscribe(reason => {
      if (reason === 'finished') {
        window.history.back();
      }
    });
  }
}
