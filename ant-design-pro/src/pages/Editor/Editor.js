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
} from 'antd'
import { getTimeDistance } from '@/utils/utils';
import styles from './Editor.less';
import request from '@/utils/request';
import {Form, Select} from "antd/lib/index";

const FormItem = Form.Item;
const { Option } = Select;
const { RangePicker } = DatePicker;
const { TextArea } = Input;

@Form.create()
class Editor extends Component {
  constructor(props) {
    super(props);
    this.editor
  }

  state = {
  };


  componentDidMount = () => {
    const { dispatch } = this.props;
    let _this = this
    $(function() {
      let testEditor = editormd("editormd", {
        path : "/editor.md/lib/", // Autoload modules mode, codemirror, marked... dependents libs path
        // theme : "dark",
        // previewTheme : "dark",
        // editorTheme : "pastel-on-dark",
        // markdown : md,
        codeFold : true,
        //syncScrolling : false,
        saveHTMLToTextarea : true,    // 保存 HTML 到 Textarea
        searchReplace : true,
        //watch : false,                // 关闭实时预览
        htmlDecode : "style,script,iframe|on*",            // 开启 HTML 标签解析，为了安全性，默认不开启
        //toolbar  : false,             //关闭工具栏
        //previewCodeHighlight : false, // 关闭预览 HTML 的代码块高亮，默认开启
        emoji : true,
        taskList : true,
        tocm            : true,         // Using [TOCM]
        tex : true,                   // 开启科学公式TeX语言支持，默认关闭
        flowChart : true,             // 开启流程图支持，默认关闭
        sequenceDiagram : true,       // 开启时序/序列图支持，默认关闭,
        //dialogLockScreen : false,   // 设置弹出层对话框不锁屏，全局通用，默认为true
        //dialogShowMask : false,     // 设置弹出层对话框显示透明遮罩层，全局通用，默认为true
        //dialogDraggable : false,    // 设置弹出层对话框不可拖动，全局通用，默认为true
        //dialogMaskOpacity : 0.4,    // 设置透明遮罩层的透明度，全局通用，默认值为0.1
        //dialogMaskBgColor : "#000", // 设置透明遮罩层的背景颜色，全局通用，默认为#fff
        imageUpload : true,
        imageFormats : ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
        imageUploadURL : "./php/upload.php",
        onload : function() {
          console.log('onload', this);
          //this.fullscreen();
          //this.unwatch();
          //this.watch().fullscreen();
          //this.setMarkdown("#PHP");
          //this.width("100%");
          //this.height(480);
          //this.resize("100%", 640);
        }
      });

      /*
      // or
      var editor = editormd({
          id   : "editormd",
          path : "../lib/"
      });
      */
      $("#goto-line-btn").bind("click", function(){
        testEditor.gotoLine(90);
      });

      $("#show-btn").bind('click', function(){
        testEditor.show();
      });

      $("#hide-btn").bind('click', function(){
        testEditor.hide();
      });

      $("#get-md-btn").bind('click', function(){
        // dispatch({
        //   type: 'editor/saveMarkdown',
        //   payload: { markdown: testEditor.getMarkdown() },
        // });
        request('/api/knowledge/knowledge', {
          method: 'POST',
          body: {
            markdown: testEditor.getMarkdown(),
            html: testEditor.getHTML(),
            title: 'test',
          },
        }).then(o => {
          console.log('o', o)
        });
        alert(testEditor.getMarkdown());
      });

      $("#get-html-btn").bind('click', function() {
        request('/api/editor/deleteMarkdown', {
          method: 'DELETE',
          body: testEditor.getMarkdown(),
            // method: 'post',
          // },
        });
        alert(testEditor.getHTML());
      });

      $("#watch-btn").bind('click', function() {
        testEditor.watch();
      });

      $("#unwatch-btn").bind('click', function() {
        testEditor.unwatch();
      });

      $("#preview-btn").bind('click', function() {
        testEditor.previewing();
      });

      $("#fullscreen-btn").bind('click', function() {
        testEditor.fullscreen();
      });

      $("#show-toolbar-btn").bind('click', function() {
        testEditor.showToolbar();
      });

      $("#close-toolbar-btn").bind('click', function() {
        testEditor.hideToolbar();
      });

      $("#toc-menu-btn").click(function(){
        testEditor.config({
          tocDropdown   : true,
          tocTitle      : "目录 Table of Contents",
        });
      });

      $("#toc-default-btn").click(function() {
        testEditor.config("tocDropdown", false);
      });
      _this.editor = testEditor
    });

  }

  componentWillUnmount() {
  }

  handleSubmit = e => {
    const { dispatch, form } = this.props;
    e.preventDefault();
    form.validateFieldsAndScroll((err, values) => {
      console.log(err, values)
      if (!err) {
        request('/api/knowledge/knowledge', {
          method: 'POST',
          body: {
            markdown: this.editor.getMarkdown(),
            html: this.editor.getHTML(),
            title: values.title,
          },
        }).then(o => {
          console.log('o', o)
          alert('已操作')
        });
      }
    });
  };
  render() {
    const {
      form: { getFieldDecorator, getFieldValue },
    } = this.props;

    const formItemLayout = {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 7 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 12 },
        md: { span: 10 },
      },
    };

    const submitFormLayout = {
      wrapperCol: {
        xs: { span: 24, offset: 0 },
        sm: { span: 10, offset: 7 },
      },
    };

    return (
      <div className={ styles.EditorContainer }>
        <Form onSubmit={this.handleSubmit} hideRequiredMark style={{ marginTop: 8 }}>
          <FormItem {...formItemLayout} label={<FormattedMessage id="form.title.label" />}>
            {getFieldDecorator('title', {
              rules: [
                {
                  required: true,
                  message: formatMessage({ id: 'validation.title.required' }),
                },
              ],
            })(<Input placeholder={formatMessage({ id: 'form.title.placeholder' })} />)}
          </FormItem>
          <div style={{ height: '500px' }}>
              <div id="editormd">
                {/*<textarea style={{display:'none'}} value={"### Hello Editor.md !"}></textarea>*/}
              </div>
          </div>
          <FormItem {...submitFormLayout} style={{ marginTop: 32 }}>
            <Button type="primary" htmlType="submit">
              <FormattedMessage id="form.submit" />
            </Button>
            <Button style={{ marginLeft: 8 }}>
              <FormattedMessage id="form.save" />
            </Button>
          </FormItem>
        </Form>
        <div className={ styles.btns }>
          <Button id="goto-line-btn">Goto line 90</Button>
          <Button id="show-btn">Show editor</Button>
          <Button id="hide-btn">Hide editor</Button>
          <Button id="get-md-btn">Get Markdown</Button>
          <Button id="get-html-btn">Get HTML</Button>
          <Button id="watch-btn">Watch</Button>
          <Button id="unwatch-btn">Unwatch</Button>
          <Button id="preview-btn">Preview HTML (Press Shift + ESC cancel)</Button>
          <Button id="fullscreen-btn">Fullscreen (Press ESC cancel)</Button>
          <Button id="show-toolbar-btn">Show toolbar</Button>
          <Button id="close-toolbar-btn">Hide toolbar</Button>
          <Button id="toc-menu-btn">ToC Dropdown menu</Button>
          <Button id="toc-default-btn">ToC default</Button>
        </div>
      </div>
    );
  }
}

export default Editor;
