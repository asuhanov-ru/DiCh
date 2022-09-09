import dayjs from 'dayjs';

export interface IOcrTasks {
  id?: number;
  mediaId?: number | null;
  pageNumber?: number | null;
  jobStatus?: string | null;
  createTime?: string | null;
  startTime?: string | null;
  stopTime?: string | null;
}

export const defaultValue: Readonly<IOcrTasks> = {};
