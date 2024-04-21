export class NotificationModel {
  constructor(
    public id: number,
    public lastName: string,
    public createdAt: Date,
    public title: string,
    public content: string,
    public status: string,
    public url: string
  ) {}
}
