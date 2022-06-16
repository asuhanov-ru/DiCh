export interface ICollections {
  id?: number;
  name?: string;
  description?: string | null;
}

export const defaultValue: Readonly<ICollections> = {};
