import { IMedia } from 'app/shared/model/media.model';

export interface IMediaStructure {
  id?: number;
  objName?: string;
  objType?: string;
  parentId?: number;
  tag?: string;
  media?: IMedia;
}

export const defaultValue: Readonly<IMediaStructure> = {};
