import { stringify } from 'qs';
import request from '@/utils/request';

export async function list(params) {
  return request(`/api/wechat/jp/list?${stringify(params)}`);
}

// export async function remove(params) {
//   return request('/api/wechat/fz/delete', {
//     method: 'POST',
//     body: {
//       ...params,
//       method: 'delete',
//     },
//   });
// }

export async function add(params) {
  return request('/api/wechat/jp/save', {
    method: 'POST',
    body: {
      ...params,
      method: 'post',
    },
  });
}

// export async function update(params) {
//   return request('/api/wechat/jp/update', {
//     method: 'POST',
//     body: {
//       ...params,
//       method: 'update',
//     },
//   });
// }

export async function queryById(params) {
  return request(`/api/wechat/jp/queryById?${stringify(params)}`);
}


