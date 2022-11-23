import { IPageImage } from 'app/shared/model/page-image.model';

export interface IPageText {
  id?: number;
  page_id?: string | null;
  text?: string | null;
  pageImage?: IPageImage | null;
}

export const defaultValue: Readonly<IPageText> = {};
