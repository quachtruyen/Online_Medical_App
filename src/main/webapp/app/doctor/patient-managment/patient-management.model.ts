export interface IPatient {
  id?: number;
  login?: string;
  firstName?: string | null;
  lastName?: string | null;
  address?: string | null;
  healthStatus?: string | null;
  diseaseSymptoms?: string | null;
  prescription?: string | null;
  position?: string | null;
  hospital?: string | null;
  specialize?: string | null;
  email?: string;
  activated?: boolean;
  langKey?: string;
  authorities?: string[];
  createdBy?: string;
  createdDate?: Date;
  lastModifiedBy?: string;
  lastModifiedDate?: Date;
  imageCertificate?: string;
}

export class Patient implements IPatient {
  constructor(
    public id?: number,
    public login?: string,
    public firstName?: string | null,
    public lastName?: string | null,
    public address?: string | null,
    public healthStatus?: string | null,
    public diseaseSymptoms?: string | null,
    public prescription?: string | null,
    public position?: string | null,
    public hospital?: string | null,
    public specialze?: string | null,
    public email?: string,
    public activated?: boolean,
    public langKey?: string,
    public authorities?: string[],
    public createdBy?: string,
    public createdDate?: Date,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Date,
    public imageCertificate?: string
  ) {}
}
