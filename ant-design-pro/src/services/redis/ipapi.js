import { stringify } from 'qs';
import request from '@/utils/request';

export async function queryRule(params) {
  return request(`/api/redis/ip/list?${stringify(params)}`);
}

export async function removeRule(params) {
  return request('/api/redis/ip/delete', {
    method: 'POST',
    body: {
      ...params,
      method: 'delete',
    },
  });
}

export async function addRule(params) {
  return request('/api/redis/ip/save', {
    method: 'POST',
    body: {
      ...params,
      method: 'post',
    },
  });
}

export async function updateRule(params) {
  return request('/api/redis/ip/update', {
    method: 'POST',
    body: {
      ...params,
      method: 'update',
    },
  });
}

export async function queryById(params) {
  return request(`/api/redis/ip/queryRedisGroupById?${stringify(params)}`);
}

export async function exportRedisMonitor(params) {
  return request(`/api/redis/monitor/getRedisExcel`);
}

export async function queryRedisIp(params) {
  return request(`/api/redis/ip/list4Select?${stringify(params)}`);
}

