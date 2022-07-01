import axios from 'axios';

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
