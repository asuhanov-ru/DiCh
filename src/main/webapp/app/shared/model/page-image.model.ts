import { IPageWord } from 'app/shared/model/page-word.model';

export interface IPageImage {
  id?: number;
  image_file_name?: string | null;
  pageWords?: IPageWord[] | null;
}

export const defaultValue: Readonly<IPageImage> = {};
