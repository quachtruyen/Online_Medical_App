export class DailyHealthStatus {
  constructor(
    public date?: Date,
    public id?: number,
    public healthStatus?: string,
    public symptoms?: string,
    public notes?: string,
    public createdAt?: Date,
    public doctorActionId?: number,
    public image?: string
  ) {}
}
