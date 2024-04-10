import { IAuthor } from 'app/shared/model/author.model';
import { IMedia } from 'app/shared/model/media.model';

export interface IBook {
  id?: number;
  name?: string | null;
  description?: string | null;
  mediaStartPage?: number | null;
  mediaEndPage?: number | null;
  author?: IAuthor | null;
  media?: IMedia | null;
}

export const defaultValue: Readonly<IBook> = {};
