import { stringify } from 'qs';
import request from '@/utils/request';

export async function queryRule(params) {
  return request(`/api/redis/group/list?${stringify(params)}`);
}

export async function removeRule(params) {
  return request('/api/redis/group/delete', {
    method: 'POST',
    body: {
      ...params,
      method: 'delete',
    },
  });
}

export async function addRule(params) {
  return request('/api/redis/group/save', {
    method: 'POST',
    body: {
      ...params,
      method: 'post',
    },
  });
}

export async function updateRule(params) {
  return request('/api/redis/group/update', {
    method: 'POST',
    body: {
      ...params,
      method: 'update',
    },
  });
}

export async function queryRedisGroupById(params) {
  return request(`/api/redis/group/queryRedisGroupById?${stringify(params)}`);
}

export async function queryRedisGroup(params) {
  return request(`/api/redis/group/list4Select`);
}
