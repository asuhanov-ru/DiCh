import { IPageImage } from 'app/shared/model/page-image.model';

export interface IPageWord {
  id?: number;
  s_word?: string | null;
  n_top?: number | null;
  n_left?: number | null;
  n_heigth?: number | null;
  n_width?: number | null;
  n_idx?: number | null;
  pageImage?: IPageImage | null;
}

export const defaultValue: Readonly<IPageWord> = {};
