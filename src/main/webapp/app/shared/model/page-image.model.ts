import { IPageWord } from 'app/shared/model/page-word.model';

export interface IPageImage {
  id?: number;
  imageContentType?: string | null;
  image?: string | null;
  pageWords?: IPageWord[] | null;
}

export const defaultValue: Readonly<IPageImage> = {};
