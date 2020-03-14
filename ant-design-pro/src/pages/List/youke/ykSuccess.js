import React, { PureComponent, Fragment } from 'react';
import { connect } from 'dva';
import moment from 'moment';
import {
  Table,
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
class YkSuccess extends PureComponent {
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
    const { modalVisible, form, handleModalVisible, data } = this.props;
   
    const columns = [
      {
        title: '游戏',
        dataIndex: 'yxTp',
        render: val => {
          if (val == null) {
            return ""
          } else if (val == 1) {
            return <span style={{color: 'black'}}>玩筛子</span>
          } else {
            return <span style={{color: 'black'}}>病毒大作战</span>
          }
        },
      },
      {
        title: '酒票',
        dataIndex: 'num',
      },
    ];
    
    return (
      <Modal
        destroyOnClose
        title={"我的战绩"}
        visible={modalVisible}
        onOk={this.okHandle}
        onCancel={() => handleModalVisible(false)}
      >
        
    <Table dataSource={data} columns={columns} />
      </Modal>
    );
  }
}

export default YkSuccess;