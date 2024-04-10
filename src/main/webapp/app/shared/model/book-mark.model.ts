export interface IBookMark {
  id?: number;
  bookMarkUUID?: string | null;
  mediaId?: number | null;
  pageNumber?: number | null;
  textBlockUUID?: string | null;
  anchor?: number | null;
  label?: string | null;
}

export const defaultValue: Readonly<IBookMark> = {};
