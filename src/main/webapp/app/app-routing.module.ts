import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { errorRoute } from './layouts/error/error.route';
import { navbarRoute } from './layouts/navbar/navbar.route';
import { DEBUG_INFO_ENABLED } from 'app/app.constants';
import { Authority } from 'app/config/authority.constants';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

const LAYOUT_ROUTES = [navbarRoute, ...errorRoute];

@NgModule({
  imports: [
    RouterModule.forRoot(
      [
        {
          path: 'admin',
          data: {
            authorities: [Authority.ADMIN],
          },
          canActivate: [UserRouteAccessService],
          loadChildren: () => import('./admin/admin-routing.module').then(m => m.AdminRoutingModule),
        },
        {
          path: 'doctor',
          data: {
            authorities: [Authority.DOCTOR, Authority.ADMIN],
          },
          canActivate: [UserRouteAccessService],
          loadChildren: () => import('./doctor/doctor-routing.module').then(m => m.DoctorRoutingModule),
        },
        {
          path: 'daily-health-status',
          canActivate: [UserRouteAccessService],
          loadChildren: () =>
            import('./daily-health-status/daily-health-status-management.module').then(m => m.DailyHealthStatusManagementModule),
        },
        // {
        //   path: 'patient',
        //   data: {
        //     authorities: [Authority.PATIENT],
        //   },
        //   canActivate: [UserRouteAccessService],
        //   loadChildren: () => import('./patient/patient-routing.module').then(m => m.PatientRoutingModule),
        // },
        {
          path: 'account',
          loadChildren: () => import('./account/account.module').then(m => m.AccountModule),
        },
        {
          path: 'login',
          loadChildren: () => import('./login/login.module').then(m => m.LoginModule),
        },
        ...LAYOUT_ROUTES,
      ],
      { enableTracing: DEBUG_INFO_ENABLED }
    ),
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}
