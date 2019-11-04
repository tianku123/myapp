import { queryRedisIp } from '@/services/redis/ipapi';
import { queryRedisGroup } from '@/services/redis/groupapi';
import { queryListPageable } from '@/services/redis/monitorapi';
import {
  message,
} from 'antd';

export default {
  namespace: 'redismonitor',

  state: {
    data: {
      list: [],
      pagination: {},
    },
    fetchByIdData:{},
    redisGroupData:[],
    redisIpData:[],
  },

  effects: {
    *fetch({ payload }, { call, put }) {
      const response = yield call(queryListPageable, payload);
      let { data } = response;
      yield put({
        type: 'save',
        payload: {
          list: data ? response.data.rows : [],
          pagination: data ? {
            total: response.data.total,
            pageSize: response.data.pageSize,
            current: response.data.current
          } : {}
        },
      });
    },
    *add({ payload, callback }, { call, put }) {
      const response = yield call(addRule, payload);
      if (response.status == 200 && response.data > 0) {
        message.success('添加成功');
      } else {
        message.success('添加失败');
      }
      yield put({
        type: 'save',
        payload: response,
      });
      if (callback) callback();
    },
    *remove({ payload, callback }, { call, put }) {
      const response = yield call(removeRule, payload);
      if (response.status == 200 && response.data > 0) {
        message.success('删除成功');
      } else {
        message.success('删除失败');
      }
      // yield put({
      //   type: 'save',
      //   payload: response,
      // });
      if (callback) callback();
    },
    *fetchById({ payload, callback }, { call, put }) {
      const response = yield call(queryById, payload);
      yield put({
        type: 'fetchByIdData',
        payload: response,
      });
      if (callback) callback();
    },
    *redisGroup({ payload, callback }, { call, put }) {
      const response = yield call(queryRedisGroup, payload);
      yield put({
        type: 'redisGroupData',
        payload: response,
      });
      if (callback) callback(response);
    },
    *redisIp({ payload, callback }, { call, put }) {
      const response = yield call(queryRedisIp, payload);
      yield put({
        type: 'redisIpData',
        payload: response,
      });
      if (callback) callback(response);
    },
    *update({ payload, callback }, { call, put }) {
      const response = yield call(updateRule, payload);
      yield put({
        type: 'save',
        payload: response,
      });
      if (callback) callback();
    },
    *exportRedisMonitor({ payload, callback }, { call, put }) {
      const response = yield call(exportRedisMonitor, payload);
      // yield put({
      //   type: 'save',
      //   payload: response,
      // });
      // if (callback) callback();
    },
  },

  reducers: {
    save(state, action) {
      return {
        ...state,
        data: action.payload,
        fetchByIdData: {},
      };
    },
    fetchByIdData(state, action) {
      return {
        ...state,
        fetchByIdData: action.payload.data,
      };
    },
    redisGroupData(state, action) {
      return {
        ...state,
        redisGroupData: action.payload.data,
      };
    },
    redisIpData(state, action) {
      return {
        ...state,
        redisIpData: action.payload.data,
      };
    },
  },
};
