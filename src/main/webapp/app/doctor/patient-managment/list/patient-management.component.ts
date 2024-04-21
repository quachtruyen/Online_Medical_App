import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatest } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { FormBuilder } from '@angular/forms';

import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/config/pagination.constants';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';

import { PatientManagementService } from '../service/patient-management.service';
import { User } from 'app/admin/user-management/user-management.model';
import { DoctorActionDialogComponent } from '../doctor-action/doctor-action-dialog.component';
import { PatientService } from '../../../admin/patient-management/patient.service';
import { DailyHealthStatus } from '../daily-health-status.model';

@Component({
  selector: 'jhi-patient-mgmt',
  templateUrl: './patient-management.component.html',
})
export class PatientManagementComponent implements OnInit {
  users: User[] | null = null;
  isLoading = false;
  healthStatuses: string[] = [];
  patientStatus: string[] = ['ACCEPT', 'FINISH'];
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;

  editForm = this.fb.group({
    status: '',
    patientStatus: '',
  });

  constructor(
    private patientManagementService: PatientManagementService,
    private patientService: PatientService,
    private accountService: AccountService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private modalService: NgbModal,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    // Khi vào trang web sẽ chạy hàm này đầu tiên
    this.patientService.healtStatsus().subscribe(healthStatuses => (this.healthStatuses = healthStatuses));
    this.handleNavigation();
  }

  search(): string {
    return 'dkkkkkk';
  }

  trackIdentity(index: number, item: User): number {
    return item.id!;
  }

  loadAll(): void {
    this.isLoading = true;
    this.patientManagementService
      .query(
        {
          page: this.page - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
        },
        this.editForm.get('status')?.value,
        this.editForm.get('patientStatus')?.value
      )
      .subscribe(
        (res: HttpResponse<User[]>) => {
          this.isLoading = false;
          this.onSuccess(res.body, res.headers);
        },
        () => (this.isLoading = false)
      );
  }

  clear(): void {
    this.editForm.setValue({ status: '', patientStatus: '' });
    this.patientManagementService
      .query(
        {
          page: this.page - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
        },
        this.editForm.get('status')?.value,
        this.editForm.get('patientStatus')?.value
      )
      .subscribe(
        (res: HttpResponse<User[]>) => {
          this.isLoading = false;
          this.onSuccess(res.body, res.headers);
        },
        () => (this.isLoading = false)
      );
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

  private handleNavigation(): void {
    combineLatest([this.activatedRoute.data, this.activatedRoute.queryParamMap]).subscribe(([data, params]) => {
      const page = params.get('page');
      this.page = page !== null ? +page : 1;
      const sort = (params.get(SORT) ?? data['defaultSort']).split(',');
      this.predicate = sort[0];
      this.ascending = sort[1] === ASC;
      this.loadAll();
    });
  }

  private sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? ASC : DESC)];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  private onSuccess(users: User[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.users = users;
    console.error('aaaa', this.users);
  }

  openAction(dailyHealthStatus?: DailyHealthStatus) {
    const modalRef = this.modalService.open(DoctorActionDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.dailyHealthStatus = dailyHealthStatus;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
