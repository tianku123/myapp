import { stringify } from 'qs';
import request from '@/utils/request';

export async function queryListPageable(params) {
  return request(`/api/redis/monitor/listPageable?${stringify(params)}`);
}

