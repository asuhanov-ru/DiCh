export interface IPageImage {
  id?: number;
  imageContentType?: string | null;
  image?: string | null;
}

export const defaultValue: Readonly<IPageImage> = {};
