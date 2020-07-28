import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICollections, defaultValue } from 'app/shared/model/collections.model';

export const ACTION_TYPES = {
  FETCH_COLLECTIONS_LIST: 'collections/FETCH_COLLECTIONS_LIST',
  FETCH_COLLECTIONS: 'collections/FETCH_COLLECTIONS',
  CREATE_COLLECTIONS: 'collections/CREATE_COLLECTIONS',
  UPDATE_COLLECTIONS: 'collections/UPDATE_COLLECTIONS',
  DELETE_COLLECTIONS: 'collections/DELETE_COLLECTIONS',
  RESET: 'collections/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICollections>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type CollectionsState = Readonly<typeof initialState>;

// Reducer

export default (state: CollectionsState = initialState, action): CollectionsState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_COLLECTIONS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_COLLECTIONS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_COLLECTIONS):
    case REQUEST(ACTION_TYPES.UPDATE_COLLECTIONS):
    case REQUEST(ACTION_TYPES.DELETE_COLLECTIONS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_COLLECTIONS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_COLLECTIONS):
    case FAILURE(ACTION_TYPES.CREATE_COLLECTIONS):
    case FAILURE(ACTION_TYPES.UPDATE_COLLECTIONS):
    case FAILURE(ACTION_TYPES.DELETE_COLLECTIONS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_COLLECTIONS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_COLLECTIONS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_COLLECTIONS):
    case SUCCESS(ACTION_TYPES.UPDATE_COLLECTIONS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_COLLECTIONS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/collections';

// Actions

export const getEntities: ICrudGetAllAction<ICollections> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_COLLECTIONS_LIST,
  payload: axios.get<ICollections>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ICollections> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_COLLECTIONS,
    payload: axios.get<ICollections>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ICollections> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_COLLECTIONS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICollections> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_COLLECTIONS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICollections> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_COLLECTIONS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
