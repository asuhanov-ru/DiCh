import { ICollections } from 'app/shared/model/collections.model';
import { IBook } from 'app/shared/model/book.model';

export interface IMedia {
  id?: number;
  fileName?: string;
  fileType?: string;
  fileDesc?: string | null;
  collections?: ICollections | null;
  books?: IBook[] | null;
}

export const defaultValue: Readonly<IMedia> = {};
