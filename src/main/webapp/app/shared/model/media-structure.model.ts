import { IMedia } from 'app/shared/model/media.model';

export interface IMediaStructure {
  id?: number;
  objName?: string | null;
  objType?: string | null;
  parentId?: number | null;
  tag?: string | null;
  media?: IMedia | null;
}

export const defaultValue: Readonly<IMediaStructure> = {};
