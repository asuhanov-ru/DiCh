import { ICollections } from 'app/shared/model/collections.model';

export interface IMedia {
  id?: number;
  fileName?: string;
  fileType?: string;
  fileDesc?: string | null;
  collections?: ICollections | null;
}

export const defaultValue: Readonly<IMedia> = {};
