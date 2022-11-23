import axios from 'axios';
import { createAsyncThunk, isFulfilled, isPending } from '@reduxjs/toolkit';
import { loadMoreDataWhenScrolled, parseHeaderForLinks } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { IQueryParams, createEntitySlice, EntityState, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import { IPageWord, defaultValue } from 'app/shared/model/page-word.model';

export type IPageWordsQueryParams = {
  id: string | number;
  pageNumber: string | number;
};

export type IPageWordQueryParams = {
  id: string | number;
};

const initialState: EntityState<IPageWord> = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

const apiUrl = 'api/v2/page-ocr';

// Actions

export const getEntities = createAsyncThunk('ocrTransfer/fetch_entity_list', async ({ id, pageNumber }: IPageWordsQueryParams) => {
  const requestUrl = `${apiUrl}?pageNumber.equals=${pageNumber}&mediaId.equals=${id}`;
  return axios.get<IPageWord[]>(requestUrl);
});

export const getEntity = createAsyncThunk(
  'ocrTransfer/fetch_entity',
  async ({ id }: IPageWordQueryParams) => {
    const requestUrl = `${apiUrl}/${id}`;
    return axios.get<IPageWord>(requestUrl);
  },
  { serializeError: serializeAxiosError }
);

export const createEntity = createAsyncThunk(
  'ocrTransfer/create_entity',
  async (entity: IPageWord, thunkAPI) => {
    return axios.post<IPageWord>(apiUrl, cleanEntity(entity));
  },
  { serializeError: serializeAxiosError }
);

// slice

export const OcrTransferSlice = createEntitySlice({
  name: 'ocrTransfer',
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

export const { reset } = OcrTransferSlice.actions;

// Reducer
export default OcrTransferSlice.reducer;
