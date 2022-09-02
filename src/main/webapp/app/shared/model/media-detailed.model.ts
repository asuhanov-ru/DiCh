import { ICollections } from 'app/shared/model/collections.model';

export interface IMediaDetailed {
  id?: number;
  fileName?: string;
  fileType?: string;
  fileDesc?: string | null;
  collections?: ICollections | null;
  pagesTotal?: Number;
}

export const defaultValue: Readonly<IMediaDetailed> = {};
