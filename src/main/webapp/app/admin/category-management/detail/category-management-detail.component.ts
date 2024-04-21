import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Category } from '../category-management.model';

@Component({
  selector: 'jhi-category-mgmt-detail',
  templateUrl: './category-management-detail.component.html',
})
export class CategoryManagementDetailComponent implements OnInit {
  category: Category | null = null;

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.data.subscribe(({ category }) => {
      this.category = category;
    });
  }
}
