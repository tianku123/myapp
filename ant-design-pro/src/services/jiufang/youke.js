import { stringify } from 'qs';
import request from '@/utils/request';

export async function list(params) {
  return request(`/api/wechat/yk/list?${stringify(params)}`);
}

export async function remove(params) {
  return request('/api/wechat/yk/delete', {
    method: 'POST',
    body: {
      ...params,
      method: 'delete',
    },
  });
}

export async function add(params) {
  return request('/api/wechat/yk/save', {
    method: 'POST',
    body: {
      ...params,
      method: 'post',
    },
  });
}

export async function update(params) {
  return request('/api/wechat/yk/update', {
    method: 'POST',
    body: {
      ...params,
      method: 'update',
    },
  });
}

export async function queryById(params) {
  return request(`/api/wechat/yk/queryById?${stringify(params)}`);
}

export async function fetchYkSuccessById(params) {
  return request(`/api/wechat/yk/fetchYkSuccessById?${stringify(params)}`);
}


