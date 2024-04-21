import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
/* jhipster-needle-add-admin-module-import - JHipster will add admin modules imports here */

@NgModule({
  imports: [
    /* jhipster-needle-add-admin-module - JHipster will add admin modules here */
    RouterModule.forChild([
      {
        path: 'user-management',
        loadChildren: () => import('./user-management/user-management.module').then(m => m.UserManagementModule),
        data: {
          pageTitle: 'userManagement.home.title',
        },
      },
      {
        path: 'patient-management',
        loadChildren: () => import('./patient-management/patient-management.module').then(m => m.PatientManagementModule),
        data: {
          pageTitle: 'Quản lý bệnh nhân',
        },
      },
      {
        path: 'category-management',
        loadChildren: () => import('./category-management/category-management.module').then(m => m.CategoryManagementModule),
        data: {
          pageTitle: 'Quản lý danh mục bài viết',
        },
      },
      {
        path: 'post-management',
        loadChildren: () => import('./post-management/post-management.module').then(m => m.PostManagementModule),
        data: {
          pageTitle: 'Quản lý bài viết',
        },
      },
      {
        path: 'medicine-management',
        loadChildren: () => import('./medicine-management/medicine-management.module').then(m => m.MedicineManagementModule),
        data: {
          pageTitle: 'Quản lý thuốc',
        },
      },
      {
        path: 'docs',
        loadChildren: () => import('./docs/docs.module').then(m => m.DocsModule),
      },
      {
        path: 'configuration',
        loadChildren: () => import('./configuration/configuration.module').then(m => m.ConfigurationModule),
      },
      {
        path: 'health',
        loadChildren: () => import('./health/health.module').then(m => m.HealthModule),
      },
      {
        path: 'logs',
        loadChildren: () => import('./logs/logs.module').then(m => m.LogsModule),
      },
      {
        path: 'metrics',
        loadChildren: () => import('./metrics/metrics.module').then(m => m.MetricsModule),
      },
      {
        path: 'tracker',
        loadChildren: () => import('./tracker/tracker.module').then(m => m.TrackerModule),
      },
      /* jhipster-needle-add-admin-route - JHipster will add admin routes here */
    ]),
  ],
})
export class AdminRoutingModule {}
