import axios from 'axios';
import { createAsyncThunk, isFulfilled, isPending, isRejected } from '@reduxjs/toolkit';

import { IQueryParams, createEntitySlice, EntityState, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import { IPageOcr, defaultValue } from 'app/shared/model/page-ocr.model';

const initialState: EntityState<IPageOcr> = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

const apiUrl = 'api/page-ocr';

// Actions

export const getEntity = createAsyncThunk(
  'pageOcr/fetch_entity',
  async (id: string | number) => {
    const requestUrl = `${apiUrl}/${id}`;
    return axios.get<IPageOcr>(requestUrl);
  },
  { serializeError: serializeAxiosError }
);

// slice

export const PageOcrSlice = createEntitySlice({
  name: 'pageOcr',
  initialState,
  extraReducers(builder) {
    builder
      .addCase(getEntity.fulfilled, (state, action) => {
        state.loading = false;
        state.entity = action.payload.data;
      })
      .addMatcher(isPending(getEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.loading = true;
      });
  },
});

export const { reset } = PageOcrSlice.actions;

// Reducer
export default PageOcrSlice.reducer;
