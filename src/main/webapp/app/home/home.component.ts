import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { CategoryManagementService } from '../admin/category-management/service/category-management.service';
import { PostManagementService } from '../admin/post-management/service/post-management.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { UpdateDailyHealthDialogComponent } from '../daily-health-status/update-daily-health-status-dialog/update-daily-health-dialog.component';
import { DailyHealthStatusService } from '../daily-health-status/daily-health-status-management.service';
import { PostService } from './post/post.service';
import { ASC, DESC, ITEMS_PER_PAGE } from '../config/pagination.constants';
import { Post } from '../admin/post-management/post-management.model';
import { HttpHeaders } from '@angular/common/http';
import { CategoryService } from './category/category.service';
import { Category } from '../admin/category-management/category-management.model';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  posts: Post[] | null = null;
  categories: Category[] | null = null;

  private readonly destroy$ = new Subject<void>();

  constructor(
    private accountService: AccountService,
    private router: Router,
    private categoryManagementService: CategoryManagementService,
    private postManagementService: PostManagementService,
    private modalService: NgbModal,
    private dailyHealthStatusService: DailyHealthStatusService,
    private postService: PostService,
    private categoryService: CategoryService
  ) {}

  ngOnInit(): void {
    this.ascending = false;
    this.postService
      .query(
        {
          page: 0,
          size: 10,
          sort: ['createdAt,desc'],
        },
        ''
      )
      .subscribe((res: any) => {
        this.onSuccess(res.body, res.headers);
      });

    this.categoryService
      .query(
        {
          page: 0,
          size: 10,
          sort: ['id,desc'],
        },
        ''
      )
      .subscribe((res: any) => {
        this.onSuccessCategory(res.body, res.headers);
      });

    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => {
        this.account = account;
        if (this.account?.authorities.includes('ROLE_PATIENT')) {
          this.dailyHealthStatusService.checkIsUpdatedToday().subscribe(result => {
            if (!result) {
              this.modalService.open(UpdateDailyHealthDialogComponent, { size: 'lg', backdrop: 'static' });
              // unsubscribe not needed because closed completes on modal close
            }
          });
        }
      });
  }

  private onSuccess(posts: Post[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.posts = posts;
    console.error('hissss', this.posts);
  }

  private onSuccessCategory(categories: Category[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.categories = categories;
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  private loadCategory() {
    console.error('sss');
  }

  public loadPostByCategoryId(categoryId: number): void {
    this.postService
      .queryByCategory(
        {
          page: 0,
          size: 10,
          sort: ['createdAt,desc'],
        },
        categoryId
      )
      .subscribe((res: any) => {
        this.onSuccess(res.body, res.headers);
      });
  }

  getByIndex(index: number): string {
    return 'hipster'.concat(String(index + 1));
  }
}
