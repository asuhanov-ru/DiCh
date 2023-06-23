import axios from 'axios';
import { createAsyncThunk, isFulfilled, isPending } from '@reduxjs/toolkit';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { createEntitySlice, EntityState, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import { IPageLayout, defaultValue } from 'app/shared/model/page-layout.model';

export type IPageLayoutsQueryParams = {
  id: string | number;
  pageNumber: string | number;
};

export type IPageLayoutQueryParams = {
  id: string | number;
};

const initialState: EntityState<IPageLayout> = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

const apiUrl = 'api/v2/page-layout';

// Actions

export const getEntities = createAsyncThunk('ocrLayoutTransfer/fetch_entity_list', async ({ id, pageNumber }: IPageLayoutsQueryParams) => {
  const requestUrl = `${apiUrl}?pageNumber.equals=${pageNumber}&mediaId.equals=${id}`;
  return axios.get<IPageLayout[]>(requestUrl);
});

export const getEntity = createAsyncThunk(
  'ocrLayoutTransfer/fetch_entity',
  async ({ id }: IPageLayoutQueryParams) => {
    const requestUrl = `${apiUrl}/${id}`;
    return axios.get<IPageLayout>(requestUrl);
  },
  { serializeError: serializeAxiosError }
);

export const createEntity = createAsyncThunk(
  'ocrLayoutTransfer/create_entity',
  async (entity: IPageLayout, thunkAPI) => {
    return axios.post<IPageLayout>(apiUrl, cleanEntity(entity));
  },
  { serializeError: serializeAxiosError }
);

// slice

export const OcrLayoutTransferSlice = createEntitySlice({
  name: 'ocrLayoutTransfer',
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

export const { reset } = OcrLayoutTransferSlice.actions;

// Reducer
export default OcrLayoutTransferSlice.reducer;
