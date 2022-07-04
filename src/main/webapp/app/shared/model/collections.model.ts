import { IMedia } from 'app/shared/model/media.model';

export interface ICollections {
  id?: number;
  name?: string;
  description?: string | null;
  media?: IMedia[] | null;
}

export const defaultValue: Readonly<ICollections> = {};
