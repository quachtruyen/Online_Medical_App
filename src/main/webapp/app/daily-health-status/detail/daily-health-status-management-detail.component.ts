import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'app/admin/user-management/user-management.model';
import { DailyHealthStatus } from '../../doctor/patient-managment/daily-health-status.model';
import { ASC, DESC, ITEMS_PER_PAGE } from '../../config/pagination.constants';
import { DailyHealthStatusService } from '../daily-health-status-management.service';
import { DoctorAction } from '../../doctor/patient-managment/doctor-action.model';
import { DoctorActionService } from '../doctor-action/doctor-action.service';
import { MedicinePrescriptionModel } from '../../doctor/patient-managment/medicine-prescription.model';
import { PrescriptionService } from '../../doctor/patient-managment/service/prescription.service';
import { PrescriptionModel } from '../../doctor/patient-managment/prescription.model';
import { AuthServerProvider } from '../../core/auth/auth-jwt.service';

@Component({
  selector: 'jhi-patient-mgmt-detail',
  templateUrl: './daily-health-status-management-detail.component.html',
})
export class DailyHealthStatusManagementDetailComponent implements OnInit {
  dailyHealthStatus: DailyHealthStatus | null = null;
  token = '';
  doctorAction!: DoctorAction;
  prescription!: PrescriptionModel;
  medicines?: MedicinePrescriptionModel[] = [];
  statuses = [
    { status: 'normal', content: 'Bình thường' },
    { status: 'light', content: 'Nhẹ' },
    { status: 'emergency', content: 'Nặng' },
    { status: 'critical', content: 'Nguy kịch' },
  ];
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  constructor(
    private route: ActivatedRoute,
    private dailyHealthStatusService: DailyHealthStatusService,
    private doctorActionService: DoctorActionService,
    private prescriptionService: PrescriptionService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private auth: AuthServerProvider
  ) {}

  ngOnInit(): void {
    this.token = this.auth.getToken();
    this.route.data.subscribe(({ dailyHealthStatus }) => {
      this.dailyHealthStatus = dailyHealthStatus;
      this.page = 1;
      this.predicate = 'id';
      this.loadAllDoctorAction(dailyHealthStatus.id);
    });
  }

  transition(): void {
    this.router.navigate(['./'], {
      relativeTo: this.activatedRoute.parent,
      queryParams: {
        page: this.page,
        sort: this.predicate + ',' + (this.ascending ? ASC : DESC),
      },
    });
  }

  trackIdentity(index: number, item: User): number {
    return item.id!;
  }

  public loadAllDoctorAction(patientId: number | undefined) {
    this.doctorActionService.findByDailyHealthStatusId(patientId).subscribe((res: DailyHealthStatus) => {
      this.doctorAction = res;
      if (this.doctorAction && this.doctorAction.prescriptionId) {
        this.loadPrescription(this.doctorAction.prescriptionId);
      }
    });
  }

  public loadPrescription(id: number) {
    this.prescriptionService.find(id).subscribe(res => {
      this.prescription = res;
      this.medicines = this.prescription.medicineDTOs;
    });
  }

  private sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? ASC : DESC)];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  public getStatus(healthStatus: string): string {
    return this.statuses.filter(elm => elm.status === healthStatus)[0].content;
  }

  back(): void {
    window.history.back();
  }
}
