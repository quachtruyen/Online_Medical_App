import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { LANGUAGES } from 'app/config/language.constants';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { DailyHealthStatus } from '../../doctor/patient-managment/daily-health-status.model';
import { DailyHealthStatusService } from '../../daily-health-status/daily-health-status-management.service';
import { ASC, DESC, ITEMS_PER_PAGE } from '../../config/pagination.constants';
import { User } from '../../admin/user-management/user-management.model';
import { ActivatedRoute, Router } from '@angular/router';
import { Doctor } from '../../admin/user-management/doctor.model';
import { DoctorActionService } from '../../daily-health-status/doctor-action/doctor-action.service';
import { DoctorService } from '../../admin/user-management/service/doctor.service';
import { faUpload } from '@fortawesome/free-solid-svg-icons';
import { AuthServerProvider } from '../../core/auth/auth-jwt.service';
import { FileService } from '../../core/file/file.service';

@Component({
  selector: 'jhi-settings',
  templateUrl: './settings.component.html',
})
export class SettingsComponent implements OnInit {
  account!: Account;
  success = false;
  languages = LANGUAGES;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  doctor!: User;
  fileName = '';
  formData!: FormData;
  image?: string;
  token = '';
  dailyHealthStatusList: DailyHealthStatus[] | null = [];
  settingsForm = this.fb.group({
    firstName: [undefined, [Validators.required, Validators.minLength(1), Validators.maxLength(255)]],
    lastName: [undefined, [Validators.required, Validators.minLength(1), Validators.maxLength(255)]],
    phoneNumber: [undefined, [Validators.required, Validators.minLength(1), Validators.maxLength(255)]],
    position: [undefined, [Validators.maxLength(255)]],
    hospital: [undefined, [Validators.maxLength(255)]],
    specialize: [undefined, [Validators.maxLength(255)]],
    address: [undefined, [Validators.maxLength(255)]],
    healthStatus: [undefined, [Validators.maxLength(255)]],
    diseaseSymptoms: [undefined, [Validators.maxLength(255)]],
    prescription: [undefined, [Validators.maxLength(255)]],
    email: [undefined, [Validators.required, Validators.minLength(5), Validators.maxLength(254), Validators.email]],
    langKey: [undefined],
  });
  faUpload = faUpload;

  constructor(
    private accountService: AccountService,
    private fb: FormBuilder,
    private translateService: TranslateService,
    private dailyHealthStatusService: DailyHealthStatusService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private doctorService: DoctorService,
    private auth: AuthServerProvider,
    private fileService: FileService
  ) {}

  ngOnInit(): void {
    this.token = this.auth.getToken();
    // moi vao se chay ham nay
    this.accountService.identity().subscribe(account => {
      if (account) {
        this.settingsForm.patchValue({
          firstName: account.firstName,
          lastName: account.lastName,
          email: account.email,
          langKey: account.langKey,
          position: account.doctor ? account.doctor.position : '',
          hospital: account.doctor ? account.doctor.hospital : '',
          specialize: account.doctor ? account.doctor.specialize : '',
          address: account.patient ? account.patient.address : '',
          healthStatus: account.patient ? account.patient.healthStatus : '',
          diseaseSymptoms: account.patient ? account.patient.diseaseSymptoms : '',
          prescription: account.prescription,
          phoneNumber: account.phoneNumber,
        });

        this.account = account;

        if (this.account.patient && this.account.patient.id) {
          this.image = this.account.patient.imageCertificate;
          this.predicate = 'createdAt';
          this.page = 1;
          this.loadAllDailyHealthStatus(this.account.patient.id);
          this.doctorService.getAssignToMe().subscribe((doctor: User) => {
            this.doctor = doctor;
          });
        }
      }
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

  public loadAllDailyHealthStatus(patientId: number) {
    this.dailyHealthStatusService
      .query(
        {
          page: this.page - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
        },
        patientId
      )
      .subscribe((res: HttpResponse<DailyHealthStatus[]>) => {
        this.onSuccess(res.body, res.headers);
      });
  }

  private sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? ASC : DESC)];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  private onSuccess(dailyHealthStatusList: DailyHealthStatus[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.dailyHealthStatusList = dailyHealthStatusList;
  }

  save(): void {
    this.success = false;

    this.account.firstName = this.settingsForm.get('firstName')!.value;
    this.account.lastName = this.settingsForm.get('lastName')!.value;
    this.account.email = this.settingsForm.get('email')!.value;
    this.account.langKey = this.settingsForm.get('langKey')!.value;
    this.account.position = this.settingsForm.get('position')!.value;
    this.account.hospital = this.settingsForm.get('hospital')!.value;
    this.account.specialize = this.settingsForm.get('specialize')!.value;
    this.account.address = this.settingsForm.get('address')!.value;
    this.account.healthStatus = this.settingsForm.get('healthStatus')!.value;
    this.account.diseaseSymptoms = this.settingsForm.get('diseaseSymptoms')!.value;
    this.account.prescription = this.settingsForm.get('prescription')!.value;
    this.account.phoneNumber = this.settingsForm.get('phoneNumber')!.value;

    this.accountService.save(this.account).subscribe(() => {
      this.success = true;

      this.accountService.authenticate(this.account);

      if (this.account.langKey !== this.translateService.currentLang) {
        this.translateService.use(this.account.langKey);
      }
    });

    if (this.account.patient && this.account.patient.id && this.formData && this.formData.get('file')) {
      this.fileService.uploadFile(this.formData, this.account.patient.id, 'certificate').subscribe(() => {
        this.success = true;
      });
    }
  }

  back(): void {
    window.history.back();
  }

  onFileSelected(event) {
    const file: File = event.target.files[0];
    const formData = new FormData();

    if (file) {
      this.fileName = file.name;
      formData.append('file', file);
    } else {
      this.fileName = '';
      formData.delete('file');
    }
    this.formData = formData;
  }
}
