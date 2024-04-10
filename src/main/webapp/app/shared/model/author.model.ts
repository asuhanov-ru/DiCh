import { IBook } from 'app/shared/model/book.model';

export interface IAuthor {
  id?: number;
  name?: string | null;
  callsign?: string | null;
  description?: string | null;
  imageContentType?: string | null;
  image?: string | null;
  books?: IBook[] | null;
}

export const defaultValue: Readonly<IAuthor> = {};
