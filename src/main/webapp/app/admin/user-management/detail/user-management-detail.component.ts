import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { Doctor } from '../doctor.model';
import { DailyHealthStatus } from '../../../doctor/patient-managment/daily-health-status.model';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { DailyHealthStatusService } from '../../../daily-health-status/daily-health-status-management.service';
import { ASC, DESC, ITEMS_PER_PAGE } from '../../../config/pagination.constants';
import { User } from '../user-management.model';
import { AssginmentDialogComponent } from '../assignment/assignment-dialog.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AssignmentDeleteDialogComponent } from '../delete-assignment/assignment-delete-dialog.component';

@Component({
  selector: 'jhi-user-mgmt-detail',
  templateUrl: './user-management-detail.component.html',
})
export class UserManagementDetailComponent implements OnInit {
  user: Doctor | null = null;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  dailyHealthStatusList: DailyHealthStatus[] | null = [];

  constructor(
    private route: ActivatedRoute,
    private dailyHealthStatusService: DailyHealthStatusService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.route.data.subscribe(({ user }) => {
      this.user = user;
      this.page = 0;
      this.predicate = 'id';
      if (this.user && this.user.assignment && this.user.assignment.patientId) {
        this.loadAllDailyHealthStatus(this.user.assignment.patientId);
      }
    });
  }

  private onSuccess(dailyHealthStatusList: DailyHealthStatus[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.dailyHealthStatusList = dailyHealthStatusList;
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

  assignUser(user: User): void {
    const modalRef = this.modalService.open(AssginmentDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.user = user;
    // unsubscribe not needed because closed completes on modal close
  }

  deleteAssignment(id: number) {
    const modalRef = this.modalService.open(AssignmentDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.id = id;
    // unsubscribe not needed because closed completes on modal close
  }
}
