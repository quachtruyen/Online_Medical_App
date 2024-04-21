import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { LANGUAGES } from 'app/config/language.constants';
import { Category } from '../category-management.model';
import { CategoryManagementService } from '../service/category-management.service';

@Component({
  selector: 'jhi-user-mgmt-update',
  templateUrl: './category-management-update.component.html',
})
export class CategoryManagementUpdateComponent implements OnInit {
  category!: Category;
  languages = LANGUAGES;
  authorities: string[] = [];
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: ['', [Validators.maxLength(255)]],
  });

  constructor(private categoryService: CategoryManagementService, private route: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.route.data.subscribe(({ category }) => {
      if (category) {
        this.category = category;

        this.updateForm(category);
      }
    });
    this.categoryService.authorities().subscribe(authorities => (this.authorities = authorities));
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    this.updateCategory(this.category);
    console.error('sadasdsa', this.category);
    if (this.category.id !== undefined) {
      this.categoryService.update(this.category).subscribe(
        () => this.onSaveSuccess(),
        () => this.onSaveError()
      );
    } else {
      this.categoryService.create(this.category).subscribe(
        () => this.onSaveSuccess(),
        () => this.onSaveError()
      );
    }
  }

  private updateForm(category: Category): void {
    this.editForm.patchValue({
      id: category.id,
      name: category.name,
    });
  }

  private updateCategory(category: Category): void {
    category.name = this.editForm.get(['name'])!.value;
  }

  private onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError(): void {
    this.isSaving = false;
  }
}
