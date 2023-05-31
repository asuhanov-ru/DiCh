import { ITextBlock } from 'app/shared/model/text-block.model';

export interface IInlineStyleRanges {
  id?: number;
  startPos?: number | null;
  stopPos?: number | null;
  textBlock?: ITextBlock | null;
}

export const defaultValue: Readonly<IInlineStyleRanges> = {};
