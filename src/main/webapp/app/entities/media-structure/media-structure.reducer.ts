import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMediaStructure, defaultValue } from 'app/shared/model/media-structure.model';

export const ACTION_TYPES = {
  FETCH_MEDIASTRUCTURE_LIST: 'mediaStructure/FETCH_MEDIASTRUCTURE_LIST',
  FETCH_MEDIASTRUCTURE: 'mediaStructure/FETCH_MEDIASTRUCTURE',
  CREATE_MEDIASTRUCTURE: 'mediaStructure/CREATE_MEDIASTRUCTURE',
  UPDATE_MEDIASTRUCTURE: 'mediaStructure/UPDATE_MEDIASTRUCTURE',
  DELETE_MEDIASTRUCTURE: 'mediaStructure/DELETE_MEDIASTRUCTURE',
  RESET: 'mediaStructure/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMediaStructure>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type MediaStructureState = Readonly<typeof initialState>;

// Reducer

export default (state: MediaStructureState = initialState, action): MediaStructureState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MEDIASTRUCTURE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MEDIASTRUCTURE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_MEDIASTRUCTURE):
    case REQUEST(ACTION_TYPES.UPDATE_MEDIASTRUCTURE):
    case REQUEST(ACTION_TYPES.DELETE_MEDIASTRUCTURE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_MEDIASTRUCTURE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MEDIASTRUCTURE):
    case FAILURE(ACTION_TYPES.CREATE_MEDIASTRUCTURE):
    case FAILURE(ACTION_TYPES.UPDATE_MEDIASTRUCTURE):
    case FAILURE(ACTION_TYPES.DELETE_MEDIASTRUCTURE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_MEDIASTRUCTURE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_MEDIASTRUCTURE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_MEDIASTRUCTURE):
    case SUCCESS(ACTION_TYPES.UPDATE_MEDIASTRUCTURE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_MEDIASTRUCTURE):
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

const apiUrl = 'api/media-structures';

// Actions

export const getEntities: ICrudGetAllAction<IMediaStructure> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_MEDIASTRUCTURE_LIST,
  payload: axios.get<IMediaStructure>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IMediaStructure> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MEDIASTRUCTURE,
    payload: axios.get<IMediaStructure>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IMediaStructure> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MEDIASTRUCTURE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMediaStructure> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MEDIASTRUCTURE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMediaStructure> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MEDIASTRUCTURE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
