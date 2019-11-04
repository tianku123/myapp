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
class RedisGroupForm extends PureComponent {
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
      const { modalVisible, isSaveOrUpdate, form, handleModalVisible, data, redisGroupData } = this.props;
      
      
      let ipId = (data && data.ip) ? data.ip : null;
      let groupId = (data && data.groupId) ? data.groupId : null;
      let groupOption = redisGroupData.map(o => {
        return <Option key={o.id} value={o.id}>{o.name}</Option>
      })
      return (
        <Modal
          destroyOnClose
          title={isSaveOrUpdate ? "新建Redis地址" : "编辑Redis地址"}
          visible={modalVisible}
          onOk={this.okHandle}
          onCancel={() => handleModalVisible(false, true)}
        >
          <FormItem labelCol={{ span: 5 }} wrapperCol={{ span: 15 }} label="分组">
            {form.getFieldDecorator('groupid', { initialValue: groupId,
              rules: [{ required: true }],
            })(<Select style={{ width: 290 }}>{groupOption}</Select>)}
          </FormItem>
          <FormItem labelCol={{ span: 5 }} wrapperCol={{ span: 15 }} label="Redis地址">
            {form.getFieldDecorator('ip', { initialValue: ipId,
              rules: [{ required: true, message: '请输入IP地址格式！', 
              pattern: /(([01]{0,1}\d{0,1}\d|2[0-4]\d|25[0-5])\.){3}([01]{0,1}\d{0,1}\d|2[0-4]\d|25[0-5])/ }],
            })(<Input placeholder="请输入" />)}
          </FormItem>
        </Modal>
      );
    }
}

export default RedisGroupForm;