import { Category } from '../category-management/category-management.model';

export interface IPost {
  id?: number;
  title?: string;
  content?: string | null;
  createdBy?: string | null;
  createdAt?: Date | null;
  imageURL?: string | null;
}

export class Post implements IPost {
  constructor(
    public id?: number,
    public title?: string,
    public content?: string | null,
    public createdBy?: string,
    public createdAt?: Date | null,
    public imageUrl?: string | null,
    public published?: boolean,
    public categoryIds?: number,
    public categories?: Category[]
  ) {}
}
