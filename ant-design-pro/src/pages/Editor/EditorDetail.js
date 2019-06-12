import React, { Component } from 'react';
import { connect } from 'dva';
import { formatMessage, FormattedMessage } from 'umi/locale';
import {
  Row,
  Col,
  Icon,
  Card,
  Tabs,
  Table,
  Radio,
  DatePicker,
  Tooltip,
  Menu,
  Dropdown,
  Button,
  Input,
} from 'antd';
import { getTimeDistance } from '@/utils/utils';
import styles from './Editor.less';
import request from '@/utils/request';
import { Form, Select } from 'antd/lib/index';
import Remarkable from 'remarkable';
import marked from 'marked';
import hljs from 'highlight.js';

const FormItem = Form.Item;
const { Option } = Select;
const { RangePicker } = DatePicker;
const { TextArea } = Input;

@connect(({ editor, loading }) => ({
  editor,
  loading: loading.models.editor,
}))
@Form.create()
class EditorDetail extends Component {
  constructor(props) {
    super(props);
  }

  state = {};

  componentDidMount = () => {
    const { dispatch } = this.props;
    dispatch({
      type: 'editor/getMarkdown',
      payload: {
        id: 3,
      },
    });
  };

  componentWillUnmount() {}

  getRawMarkup() {
    // const md = new Remarkable();
    // return { __html: md.render(this.props.editor ? this.props.editor.markdown.markdownData.markdown : null) };
    marked.setOptions({
      highlight: code => hljs.highlightAuto(code).value,
    });
    return {
      __html:
        this.props.editor && this.props.editor.markdown.markdownData.markdown
          ? marked(this.props.editor.markdown.markdownData.markdown)
          : null,
    };
  }

  render() {
    console.log(this.props);
    return <div className={styles.markdownCss} dangerouslySetInnerHTML={this.getRawMarkup()} />;
  }
}

export default EditorDetail;
