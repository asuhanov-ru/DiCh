export interface ITranslation {
  id?: number;
  lang?: string | null;
  n_version?: number | null;
}

export const defaultValue: Readonly<ITranslation> = {};
