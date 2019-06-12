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
import marked from 'marked';

const FormItem = Form.Item;
const { Option } = Select;
const { RangePicker } = DatePicker;
const { TextArea } = Input;

@connect(({ editor, loading }) => ({
  editor,
  loading: loading.models.editor,
}))
@Form.create()
class EditorMdDetail extends Component {
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

  rednerMarkDown = markdown => {
    $(function() {
      editormd.markdownToHTML('test-editormd-view2', {
        markdown: markdown, //+ "\r\n" + $("#append-test").text(),
        //htmlDecode      : true,       // 开启 HTML 标签解析，为了安全性，默认不开启
        htmlDecode: 'style,script,iframe', // you can filter tags decode
        //toc             : false,
        tocm: true, // Using [TOCM]
        //tocContainer    : "#custom-toc-container", // 自定义 ToC 容器层
        //gfm             : false,
        //tocDropdown     : true,
        // markdownSourceCode : true, // 是否保留 Markdown 源码，即是否删除保存源码的 Textarea 标签
        emoji: true,
        taskList: true,
        tex: true, // 默认不解析
        flowChart: true, // 默认不解析
        sequenceDiagram: true, // 默认不解析
      });
    });
  };
  render() {
    console.log(this.props.editor.markdown);
    if (this.props.editor && this.props.editor.markdown.markdownData.markdown) {
      this.rednerMarkDown(this.props.editor.markdown.markdownData.markdown);
    }
    return (
      <div>
        <div id="test-editormd-view2">
          {/*<textarea id="append-test" style="display:none;"></textarea>*/}
        </div>
      </div>
    );
  }
}

export default EditorMdDetail;
