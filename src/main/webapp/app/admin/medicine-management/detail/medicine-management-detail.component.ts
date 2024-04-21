import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Medicine } from '../medicine-management.model';
import { AuthServerProvider } from '../../../core/auth/auth-jwt.service';

@Component({
  selector: 'jhi-user-mgmt-detail',
  templateUrl: './medicine-management-detail.component.html',
})
export class MedicineManagementDetailComponent implements OnInit {
  medicine: Medicine | null = null;
  token!: String;

  constructor(private route: ActivatedRoute, private auth: AuthServerProvider) {}

  ngOnInit(): void {
    this.token = this.auth.getToken();
    this.route.data.subscribe(({ medicine }) => {
      this.medicine = medicine;
    });
  }

  back(): void {
    window.history.back();
  }
}
