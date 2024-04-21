import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DoctorAction } from '../../../doctor/patient-managment/doctor-action.model';

@Component({
  selector: 'jhi-doctor-action-detail',
  templateUrl: './doctor-action-detail.component.html',
})
export class DoctorActionDetailComponent implements OnInit {
  doctorAction: DoctorAction | null = null;
  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.data.subscribe(({ doctorAction }) => {
      this.doctorAction = doctorAction;
    });
  }
}
