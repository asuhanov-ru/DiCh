export interface IPageLayout {
  id?: number;
  mediaId?: number | null;
  pageNumber?: number | null;
  iterator_level?: string | null;
  rect_top?: number | null;
  rect_left?: number | null;
  rect_right?: number | null;
  rect_bottom?: number | null;
  parent_id?: number | null;
  itemGUID?: string | null;
  parentGUID?: string | null;
}

export const defaultValue: Readonly<IPageLayout> = {};
