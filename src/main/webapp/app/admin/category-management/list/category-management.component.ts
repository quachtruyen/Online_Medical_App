import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatest } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { FormBuilder, Validators } from '@angular/forms';

import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/config/pagination.constants';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { CategoryManagementService } from '../service/category-management.service';
import { Category } from '../category-management.model';
import { CategoryManagementDeleteDialogComponent } from '../delete/category-management-delete-dialog.component';

@Component({
  selector: 'jhi-user-mgmt',
  templateUrl: './category-management.component.html',
})
export class CategoryManagementComponent implements OnInit {
  currentAccount: Account | null = null;
  categories: Category[] | null = null;
  isLoading = false;
  authorities: string[] = [];
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;

  editForm = this.fb.group({
    search: [],
  });

  constructor(
    private categoryService: CategoryManagementService,
    private accountService: AccountService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private modalService: NgbModal,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    // Khi vào trang web sẽ chạy hàm này đầu tiên
    this.handleNavigation();
  }

  search(): string {
    return 'dkkkkkk';
  }

  trackIdentity(index: number, item: Category): number {
    return item.id!;
  }

  deleteCategory(category: Category): void {
    const modalRef = this.modalService.open(CategoryManagementDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.category = category;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }

  loadAll(): void {
    this.isLoading = true;
    this.categoryService
      .query(
        {
          page: this.page - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
        },
        this.editForm.get('search')!.value
      )
      .subscribe(
        (res: HttpResponse<Category[]>) => {
          this.isLoading = false;
          this.onSuccess(res.body, res.headers);
        },
        () => (this.isLoading = false)
      );
  }

  clear(): void {
    this.editForm.setValue({ search: '' });
    this.categoryService
      .query(
        {
          page: this.page - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
        },
        this.editForm.get('authority')!.value
      )
      .subscribe(
        (res: HttpResponse<Category[]>) => {
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

  private onSuccess(categories: Category[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.categories = categories;
  }
}
