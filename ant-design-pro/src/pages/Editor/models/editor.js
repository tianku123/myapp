import { routerRedux } from 'dva/router';
import { message } from 'antd';
import { queryMarkdownById } from '@/services/editor/api';
import _ from 'lodash'

export default {
  namespace: 'editor',

  state: {
    markdown: {
      markdownData: {},
    },
  },

  effects: {
    *getMarkdown({ payload }, { call, put }) {
      const response = yield call(queryMarkdownById, payload);
      yield put({
        type: 'save',
        payload: response,
      });
    },
  },

  reducers: {
    save(state, action) {
      let { payload } = action
      return _.merge({}, state, { markdown: { markdownData: payload } })
    },
  },
};
