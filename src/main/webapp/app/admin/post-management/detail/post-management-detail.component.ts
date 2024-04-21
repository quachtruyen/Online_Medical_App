import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Post } from '../post-management.model';

@Component({
  selector: 'jhi-user-mgmt-detail',
  templateUrl: './post-management-detail.component.html',
})
export class PostManagementDetailComponent implements OnInit {
  post: Post | null = null;

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.data.subscribe(({ post }) => {
      this.post = post;
    });
  }
}
