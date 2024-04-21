export interface IMedicine {
  id?: number;
  name?: string;
  origin?: string;
  element?: string;
  uses?: string;
  price?: number;
}

export class Medicine implements IMedicine {
  constructor(
    public id?: number,
    public name?: string,
    public origin?: string,
    public element?: string,
    public uses?: string,
    public price?: number,
    public deleted?: boolean,
    public image?: string
  ) {}
}
