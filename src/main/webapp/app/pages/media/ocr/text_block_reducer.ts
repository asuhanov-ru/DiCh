import axios from 'axios';
import { createAsyncThunk, isFulfilled, isPending } from '@reduxjs/toolkit';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { createEntitySlice, EntityState, serializeAxiosError } from 'app/shared/reducers/reducer.utils';

import { ITextBlock, defaultValue } from 'app/shared/model/text-block.model';

export type ITextBlocksQueryParams = {
  id: string | number;
  pageNumber: string | number;
};

export type ITextBlockQueryParams = {
  id: string | number;
};

const initialState: EntityState<ITextBlock> = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

const apiUrl = 'api/v2/page-text-block';

// Actions

export const getEntities = createAsyncThunk('textBlockTransfer/fetch_entity_list', async ({ id, pageNumber }: ITextBlocksQueryParams) => {
  const requestUrl = `${apiUrl}?pageNumber.equals=${pageNumber}&mediaId.equals=${id}`;
  return axios.get<ITextBlock[]>(requestUrl);
});

export const getEntity = createAsyncThunk(
  'textBlockTransfer/fetch_entity',
  async ({ id }: ITextBlockQueryParams) => {
    const requestUrl = `${apiUrl}/${id}`;
    return axios.get<ITextBlock>(requestUrl);
  },
  { serializeError: serializeAxiosError }
);

export const createEntity = createAsyncThunk(
  'textBlockTransfer/create_entity',
  async (entity: ITextBlock, thunkAPI) => {
    return axios.post<ITextBlock>(apiUrl, cleanEntity(entity));
  },
  { serializeError: serializeAxiosError }
);

// slice

export const TextBlockTransferSlice = createEntitySlice({
  name: 'textBlockTransfer',
  initialState,
  extraReducers(builder) {
    builder
      .addCase(getEntity.fulfilled, (state, action) => {
        state.loading = false;
        state.entity = action.payload.data;
      })
      .addMatcher(isFulfilled(getEntities), (state, action) => {
        const { data, headers } = action.payload;

        return {
          ...state,
          loading: false,
          entities: data,
          totalItems: parseInt(headers['x-total-count'], 10),
        };
      })
      .addMatcher(isPending(getEntities, getEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.loading = true;
      });
  },
});

export const { reset } = TextBlockTransferSlice.actions;

// Reducer
export default TextBlockTransferSlice.reducer;
