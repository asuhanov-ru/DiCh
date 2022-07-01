export interface IPageOcr {
  id?: number;
  imageContentType?: string | null;
  image?: string | null;
}

export const defaultValue: Readonly<IPageOcr> = {};
