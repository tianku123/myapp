import { stringify } from 'qs';
import request from '@/utils/request';

export async function queryMarkdownById(params) {
  return request(`/api/knowledge/knowledge/${params.id}`);
}
