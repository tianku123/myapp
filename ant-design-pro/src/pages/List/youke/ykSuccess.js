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
    const { modalVisible, form, handleModalVisible, data, tp } = this.props;
   
    //tp 1:我的战绩，2：我的奖品
    let columns = [
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
    if (tp == 2) {
      columns = [
        {
          title: '奖品',
          dataIndex: 'prizeConfigId',
          render: val => {
            if (val == null) {
              return ""
            } else if (val == 1) {
              return <span style={{color: 'black'}}>杯子</span>
            } else if (val == 2) {
              return <span style={{color: 'black'}}>抱枕</span>
            } else if (val == 3) {
              return <span style={{color: 'black'}}>小酒礼盒</span>
            }
          },
        },
        {
          title: '个数',
          dataIndex: 'num',
        },
        {
          title: '兑奖时间',
          dataIndex: 'createTime',
          // sorter: true,
          render: val => {return val != null ? <span>{moment(val).format('YYYY-MM-DD HH:mm:ss')}</span> : ''},
        },
      ];
    }
    
    return (
      <Modal
        destroyOnClose
        title={tp == 1 ? "我的战绩" : "我的奖品"}
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