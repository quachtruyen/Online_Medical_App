import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatest } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { FormBuilder, Validators } from '@angular/forms';

import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/config/pagination.constants';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { PatientManagementDeleteDialogComponent } from '../delete/patient-management-delete-dialog.component';
import { AssginmentDialogComponent } from '../assignment/assignment-dialog.component';
import { AssignmentDeleteDialogComponent } from '../delete-assignment/assignment-delete-dialog.component';
import { UserManagementService } from '../../user-management/service/user-management.service';
import { User } from '../../user-management/user-management.model';
import { PatientService } from '../patient.service';
import { faAsterisk } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'jhi-user-mgmt',
  templateUrl: './patient-management.component.html',
})
export class PatientManagementComponent implements OnInit {
  currentAccount: Account | null = null;
  faAssign = faAsterisk;
  users: User[] | null = null;
  isLoading = false;
  healthStatuses: string[] = [];
  patientStatus: string[] = ['ACCEPT', 'WAITING', 'FINISH'];
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
    private userService: UserManagementService,
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

  setActive(user: User, isActivated: boolean): void {
    this.userService.update({ ...user, activated: isActivated }).subscribe(() => this.loadAll());
  }

  search(): string {
    return 'dkkkkkk';
  }

  trackIdentity(index: number, item: User): number {
    return item.id!;
  }

  deleteUser(user: User): void {
    const modalRef = this.modalService.open(PatientManagementDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.user = user;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }

  deleteAssignment(id: number): void {
    const modalRef = this.modalService.open(AssignmentDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.id = id;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }

  assignUser(user: User): void {
    const modalRef = this.modalService.open(AssginmentDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.user = user;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }

  viewAssignUser(user: User): void {
    const modalRef = this.modalService.open(AssginmentDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.user = user;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }

  loadAll(): void {
    this.isLoading = true;
    this.patientService
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
          console.error('load user fail');
          this. onSuccess(res.body, res.headers);
        },
        () => (this.isLoading = false)
      );
  }

  clear(): void {
    this.editForm.setValue({ status: '', patientStatus: '' });
    this.patientService
      .query(
        {
          page: this.page - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
        },
        this.editForm.get('status')!.value,
        this.editForm.get('patientStatus')!.value
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
  }
}
