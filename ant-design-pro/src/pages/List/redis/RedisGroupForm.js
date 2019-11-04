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
      const { modalVisible, isSaveOrUpdate, form, handleModalVisible, data } = this.props;
      
      
      let nameVal = null;
      // if (!isSaveOrUpdate) {
        nameVal = (data && data.name) ? data.name : null;
      // }
      
      return (
        <Modal
          destroyOnClose
          title={isSaveOrUpdate ? "新建分组" : "编辑分组"}
          visible={modalVisible}
          onOk={this.okHandle}
          onCancel={() => handleModalVisible(false, true)}
        >
          <FormItem labelCol={{ span: 5 }} wrapperCol={{ span: 15 }} label="分组名称">
            {form.getFieldDecorator('name', { initialValue: nameVal,
              rules: [{ required: true, message: '请输入至少一个字符！', min: 1 }],
            })(<Input placeholder="请输入" />)}
          </FormItem>
        </Modal>
      );
    }
}

export default RedisGroupForm;