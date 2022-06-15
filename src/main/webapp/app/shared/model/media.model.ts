import { ICollections } from 'app/shared/model/collections.model';

export interface IMedia {
  id?: number;
  fileName?: string;
  fileType?: string;
  fileDesc?: string;
  collections?: ICollections;
}

export const defaultValue: Readonly<IMedia> = {};
