import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { LANGUAGES } from 'app/config/language.constants';
import { PostManagementService } from '../service/post-management.service';
import { Post } from '../post-management.model';
import * as ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import { Category } from '../../category-management/category-management.model';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { CategoryManagementService } from '../../category-management/service/category-management.service';
import { ITEMS_PER_PAGE } from '../../../config/pagination.constants';

@Component({
  selector: 'jhi-user-mgmt-update',
  templateUrl: './post-management-update.component.html',
})
export class PostManagementUpdateComponent implements OnInit {
  public Editor = ClassicEditor;
  post!: Post;
  languages = LANGUAGES;
  categories: Category[] | null = [];
  authorities: string[] = [];
  isSaving = false;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;

  editForm = this.fb.group({
    id: [],
    title: ['', [Validators.required]],
    content: ['', [Validators.required]],
    published: [],
    categoryIds: [],
  });

  constructor(
    private postManagementService: PostManagementService,
    private categoryManagementService: CategoryManagementService,
    private route: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.route.data.subscribe(({ post }) => {
      if (post) {
        this.post = post;
        if (this.post.id === undefined) {
          // this.post.activated = true;
        }
        this.updateForm(post);
      }
    });
    this.loadAllCategory();
    this.postManagementService.authorities().subscribe(authorities => (this.authorities = authorities));
  }

  previousState(): void {
    window.history.back();
  }

  loadAllCategory(): void {
    this.categoryManagementService
      .query(
        {
          page: 0,
          size: 100,
          sort: [],
        },
        ''
      )
      .subscribe((res: HttpResponse<Category[]>) => {
        this.onSuccess(res.body, res.headers);
      });
  }

  private onSuccess(categories: Category[] | null, headers: HttpHeaders): void {
    this.categories = categories;
  }

  save(): void {
    this.isSaving = true;
    this.updatePost(this.post);
    if (this.post.id !== undefined) {
      this.postManagementService.update(this.post).subscribe(
        () => this.onSaveSuccess(),
        () => this.onSaveError()
      );
    } else {
      this.postManagementService.create(this.post).subscribe(
        () => this.onSaveSuccess(),
        () => this.onSaveError()
      );
    }
  }

  private updateForm(post: Post): void {
    this.editForm.patchValue({
      id: post.id,
      title: post.title,
      content: post.content,
      published: post.published,
      categoryIds: post.categoryIds,
    });
  }

  private updatePost(post: Post): void {
    post.title = this.editForm.get(['title'])!.value;
    post.content = this.editForm.get(['content'])!.value;
    post.published = this.editForm.get(['published'])!.value;
    post.categoryIds = this.editForm.get(['categoryIds'])!.value;
  }

  private onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError(): void {
    this.isSaving = false;
  }
}
