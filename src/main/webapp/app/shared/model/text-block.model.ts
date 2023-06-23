import { IMedia } from 'app/shared/model/media.model';

export interface ITextBlock {
  id?: number;
  pageNumber?: number | null;
  blockIndex?: number | null;
  blockUUID?: string | null;
  media?: IMedia | null;
}

export const defaultValue: Readonly<ITextBlock> = {};
