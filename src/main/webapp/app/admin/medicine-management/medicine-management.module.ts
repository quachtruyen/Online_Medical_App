import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { MedicineManagementComponent } from './list/medicine-management.component';
import { MedicineManagementDetailComponent } from './detail/medicine-management-detail.component';
import { MedicineManagementUpdateComponent } from './update/medicine-management-update.component';
import { MedicineManagementDeleteDialogComponent } from './delete/medicine-management-delete-dialog.component';
import { medicineManagementRoute } from './medicine-management.route';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(medicineManagementRoute)],
  declarations: [
    MedicineManagementComponent,
    MedicineManagementDetailComponent,
    MedicineManagementUpdateComponent,
    MedicineManagementDeleteDialogComponent,
  ],
  entryComponents: [MedicineManagementDeleteDialogComponent],
})
export class MedicineManagementModule {}
