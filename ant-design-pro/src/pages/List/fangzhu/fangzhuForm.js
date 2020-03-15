import React, { PureComponent, Fragment } from 'react';
import { connect } from 'dva';
import moment from 'moment';
import {
  Row,
  Col,
  Card,
  Form,
  Input,
  Select,
  Icon,
  Button,
  Dropdown,
  Menu,
  InputNumber,
  DatePicker,
  Modal,
  message,
  Badge,
  Divider,
  Steps,
  Radio,
} from 'antd';

const FormItem = Form.Item;
const Option = Select.Option;
// @connect(({ redisgroup, loading }) => ({
//     redisgroup,
//     loading: loading.models.redisgroup,
// }))
@Form.create()
class FangZhuForm extends PureComponent {
  constructor(props) {
    super(props);

    this.state = {
      // nameVal: [],
    };
  }

  // 替换 componentWillReceiveProps 方法
  // static getDerivedStateFromProps = (nextProps) => {
  //   if (!nextProps.isSaveOrUpdate && nextProps.data && nextProps.data.name) {// 编辑
  //     this.setState({
  //       nameVal: nextProps.data.name
  //     })
  //   }
  //   return null;
  // }


  okHandle = () => {
    this.props.form.validateFields((err, fieldsValue) => {
      if (err) return;
      this.props.form.resetFields();
      this.props.handleAdd(fieldsValue);
    });
  };

  render() {
    const { modalVisible, isSaveOrUpdate, form, handleModalVisible, data } = this.props;

    let nameVal = (data && data.name) ? data.name : null;
    let phoneVal = (data && data.phone) ? data.phone : null;
    let numVal = (data && data.num) ? data.num : null;
    if(isSaveOrUpdate) {
      nameVal = null;
      phoneVal = null;
      numVal = null;
    }
    return (
      <Modal
        destroyOnClose
        title={isSaveOrUpdate ? "新建经销商" : "编辑经销商"}
        visible={modalVisible}
        onOk={this.okHandle}
        onCancel={() => handleModalVisible(false, true)}
      >
        <FormItem labelCol={{ span: 5 }} wrapperCol={{ span: 15 }} label="名称">
          {form.getFieldDecorator('name', {
            initialValue: nameVal,
            rules: [{
              required: true, message: '请输入名称！',
            }],
          })(<Input placeholder="请输入名称！" />)}
        </FormItem>
        {isSaveOrUpdate ?
        <FormItem labelCol={{ span: 5 }} wrapperCol={{ span: 15 }} label="手机号码">
          {form.getFieldDecorator('phone', {
            rules: [{
              required: true, message: '格式不正确！',
              pattern: /^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\d{8}$/
            }],
          })(<Input placeholder="请输入微信绑定的手机号码！" />)}
        </FormItem>
        :
        <FormItem labelCol={{ span: 5 }} wrapperCol={{ span: 15 }} label="手机号码">
          {form.getFieldDecorator('phone', {
            initialValue: phoneVal,
            rules: [{
              required: true, message: '格式不正确！',
              pattern: /^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\d{8}$/
            }],
          })(<Input disabled={true} placeholder="请输入微信绑定的手机号码！" />)}
        </FormItem>
        }
        <FormItem labelCol={{ span: 5 }} wrapperCol={{ span: 15 }} label="酒票数">
          {form.getFieldDecorator('num', {
            initialValue: numVal,
            rules: [{
              required: true, message: '请输入正整数！',
              pattern: /^[1-9]\d*$/
            }],
          })(<Input placeholder="请输入分配的酒票数！" />)}
        </FormItem>
      </Modal>
    );
  }
}

export default FangZhuForm;