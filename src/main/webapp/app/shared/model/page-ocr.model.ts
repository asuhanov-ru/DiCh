import { IPageWord } from './page-word.model';

export interface IPageOcr {
  id?: number;
  imageContentType?: string | null;
  image?: string | null;
  words?: IPageWord[];
}

export const defaultValue: Readonly<IPageOcr> = {};
