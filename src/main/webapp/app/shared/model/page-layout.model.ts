export interface IPageLayout {
  id?: number;
  mediaId?: number | null;
  pageNumber?: number | null;
}

export const defaultValue: Readonly<IPageLayout> = {};
