
import { list, add, remove, update, queryById } from '@/services/jiufang/jpRank';
import {
  message,
} from 'antd';

export default {
  namespace: 'jpRank',

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
    *list({ payload }, { call, put }) {
      const response = yield call(list, payload);
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
      const response = yield call(add, payload);
      if (response.status == 200 && response.data > 0) {
        message.success('添加成功');
      } else {
        message.error(response.message);
      }
      yield put({
        type: 'save',
        payload: response,
      });
      if (callback) callback();
    },
    *remove({ payload, callback }, { call, put }) {
      const response = yield call(remove, payload);
      if (response.status == 200 && response.data > 0) {
        message.success('删除成功');
      } else {
        message.error('删除失败');
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
    // *redisGroup({ payload, callback }, { call, put }) {
    //   const response = yield call(queryRedisGroup, payload);
    //   yield put({
    //     type: 'redisGroupData',
    //     payload: response,
    //   });
    //   if (callback) callback(response);
    // },
    // *redisIp({ payload, callback }, { call, put }) {
    //   const response = yield call(queryRedisIp, payload);
    //   yield put({
    //     type: 'redisIpData',
    //     payload: response,
    //   });
    //   if (callback) callback(response);
    // },
    *update({ payload, callback }, { call, put }) {
      const response = yield call(update, payload);
      if (response.status == 200 && response.data > 0) {
        message.success('操作成功');
      } else {
        message.error(response.message);
      }
      yield put({
        type: 'save',
        payload: response,
      });
      if (callback) callback();
    },
    // *exportRedisMonitor({ payload, callback }, { call, put }) {
    //   const response = yield call(exportRedisMonitor, payload);
    //   // yield put({
    //   //   type: 'save',
    //   //   payload: response,
    //   // });
    //   // if (callback) callback();
    // },
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
