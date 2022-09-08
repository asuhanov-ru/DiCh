import axios from 'axios';
import { createAsyncThunk, isFulfilled, isPending, isRejected } from '@reduxjs/toolkit';
import { loadMoreDataWhenScrolled, parseHeaderForLinks } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { IQueryParams, createEntitySlice, EntityState, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import { IPageImage, defaultValue } from 'app/shared/model/page-image.model';

export type IPageImageQueryParams = {
  id: string | number;
  pageNumber: string | number;
};

const initialState: EntityState<IPageImage> = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

const apiUrl = 'api/v2/page-images';

// Actions

export const getEntities = createAsyncThunk('pageImageTransfer/fetch_entity_list', async ({ page, size, sort }: IQueryParams) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}&` : '?'}cacheBuster=${new Date().getTime()}`;
  return axios.get<IPageImage[]>(requestUrl);
});

export const getEntity = createAsyncThunk(
  'pageImageTransfer/fetch_entity',
  async ({ id, pageNumber }: IPageImageQueryParams) => {
    const requestUrl = `${apiUrl}/${id}?pageNumber=${pageNumber}`;
    return axios.get<IPageImage>(requestUrl);
  },
  { serializeError: serializeAxiosError }
);

export const createEntity = createAsyncThunk(
  'pageImageTransfer/create_entity',
  async (entity: IPageImage, thunkAPI) => {
    return axios.post<IPageImage>(apiUrl, cleanEntity(entity));
  },
  { serializeError: serializeAxiosError }
);

// slice

export const PageImageTransferSlice = createEntitySlice({
  name: 'pageImageTransfer',
  initialState,
  extraReducers(builder) {
    builder
      .addCase(getEntity.fulfilled, (state, action) => {
        state.loading = false;
        state.entity = action.payload.data;
      })
      .addMatcher(isFulfilled(getEntities), (state, action) => {
        const { data, headers } = action.payload;
        const links = parseHeaderForLinks(headers.link);

        return {
          ...state,
          loading: false,
          links,
          entities: loadMoreDataWhenScrolled(state.entities, data, links),
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

export const { reset } = PageImageTransferSlice.actions;

// Reducer
export default PageImageTransferSlice.reducer;
