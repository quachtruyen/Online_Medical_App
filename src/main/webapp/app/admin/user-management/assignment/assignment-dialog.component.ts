import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { User } from '../user-management.model';
import { ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { DoctorService } from '../service/doctor.service';
import { AssignmentService } from '../service/assignment.service';

@Component({
  selector: 'jhi-assignment-dialog',
  templateUrl: './assignment-dialog.component.html',
})
export class AssginmentDialogComponent implements OnInit {
  user?: User;
  users: User[] | null = null;
  isLoading = false;
  itemsPerPage = 100;
  page!: number;
  doctorIdSelected?: number;
  editForm = this.fb.group({
    search: [],
  });
  predicate: string | undefined;
  ascending: any;
  totalItems: number | undefined;
  router: any;
  activatedRoute: any;

  constructor(
    private doctorService: DoctorService,
    private assignmentService: AssignmentService,
    private activeModal: NgbActiveModal,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.page = 1;
    this.loadAll();
  }

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmAssignment(): void {
    this.assignmentService
      .create({
        doctorId: this.doctorIdSelected,
        patientId: this.user?.patient?.id,
      })
      .subscribe(() => {
        this.activeModal.close('deleted');
      });
  }

  loadAll(): void {
    this.isLoading = true;
    this.doctorService
      .query(
        {
          page: this.page - 1,
          size: this.itemsPerPage,
          sort: [],
        },
        this.editForm.get('search')?.value
      )
      .subscribe(
        (res: HttpResponse<User[]>) => {
          this.isLoading = false;
          this.onSuccess(res.body, res.headers);
        },
        () => (this.isLoading = false)
      );
  }

  private sort(): void {
    // const result = [this.predicate + ',' + (this.ascending ? ASC : DESC)];
    // if (this.predicate !== 'id') {
    //   result.push('id');
    // }
    // return result;
  }

  private onSuccess(users: User[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.users = users;
    console.error(this.users);
  }

  transition(): void {
    this.router.navigate(['./'], {
      relativeTo: this.activatedRoute.parent,
      queryParams: {
        page: this.page,
        // sort: this.predicate + ',' + (this.ascending ? ASC : DESC),
      },
    });
  }

  trackIdentity(index: number, item: User): number {
    return item.id!;
  }

  setActive(user: User, isActivated: boolean): void {
    this.doctorService.update({ ...user, activated: isActivated }).subscribe(() => this.loadAll());
  }

  onItemChange(e?: any) {
    this.doctorIdSelected = e.target.value;
    console.log(' Value is : ', e.target.value);
  }

  clear(): void {
    this.editForm.setValue({ search: '' });
    this.doctorService
      .query(
        {
          page: this.page - 1,
          size: this.itemsPerPage,
          sort: [],
        },
        ''
      )
      .subscribe(
        (res: HttpResponse<User[]>) => {
          this.isLoading = false;
          this.onSuccess(res.body, res.headers);
        },
        () => (this.isLoading = false)
      );
  }
}
