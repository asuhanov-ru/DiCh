export interface IPageWord {
  id?: number;
  s_word?: string | null;
  n_top?: number | null;
  n_left?: number | null;
  n_heigth?: number | null;
  n_width?: number | null;
  n_idx?: number | null;
  mediaId?: number | null;
  pageNumber?: number | null;
  version?: string | null;
}

export const defaultValue: Readonly<IPageWord> = {};
