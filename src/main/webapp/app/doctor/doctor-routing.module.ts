import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
/* jhipster-needle-add-admin-module-import - JHipster will add admin modules imports here */

@NgModule({
  imports: [
    /* jhipster-needle-add-admin-module - JHipster will add admin modules here */
    RouterModule.forChild([
      {
        path: 'patient-management',
        loadChildren: () => import('./patient-managment/patient-management.module').then(m => m.PatientManagementModule),
        data: {
          pageTitle: 'userManagement.home.title',
        },
      },
      /* jhipster-needle-add-admin-route - JHipster will add admin routes here */
    ]),
  ],
})
export class DoctorRoutingModule {}
