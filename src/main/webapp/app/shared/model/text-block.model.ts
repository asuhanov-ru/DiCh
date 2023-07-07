export interface ITextBlock {
  id?: number;
  mediaId?: number | null;
  pageNumber?: number | null;
  blockIndex?: number | null;
  blockUUID?: string | null;
}

export const defaultValue: Readonly<ITextBlock> = {};
