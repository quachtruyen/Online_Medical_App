import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { PostManagementComponent } from './list/post-management.component';
import { PostManagementDetailComponent } from './detail/post-management-detail.component';
import { PostManagementUpdateComponent } from './update/post-management-update.component';
import { PostManagementDeleteDialogComponent } from './delete/post-management-delete-dialog.component';
import { postManagementRoute } from './post-management.route';
import { NgSelectModule } from '@ng-select/ng-select';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(postManagementRoute), NgSelectModule],
  declarations: [
    PostManagementComponent,
    PostManagementDetailComponent,
    PostManagementUpdateComponent,
    PostManagementDeleteDialogComponent,
  ],
  entryComponents: [PostManagementDeleteDialogComponent],
})
export class PostManagementModule {}
