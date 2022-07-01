import { IPageImage } from 'app/shared/model/page-image.model';

export interface IPageWord {
  id?: number;
  word?: string | null;
  left?: number | null;
  top?: number | null;
  right?: number | null;
  bottom?: number | null;
  pageImage?: IPageImage | null;
}

export const defaultValue: Readonly<IPageWord> = {};
