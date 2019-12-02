import React, { PureComponent, Fragment } from 'react';
import { connect } from 'dva';
import moment from 'moment';
import {
  Row,
  Col,
  Card,
  Form,
  Select,
  Table,
  Button,
  DatePicker,
} from 'antd';
import StandardTable from '@/components/StandardTable';
import PageHeaderWrapper from '@/components/PageHeaderWrapper';

import styles from './RedisIpList.less';

const { MonthPicker, RangePicker } = DatePicker;
const FormItem = Form.Item;
const { Option } = Select;
const getValue = obj =>
  Object.keys(obj)
    .map(key => obj[key])
    .join(',');


/* eslint react/no-multi-comp:0 */
@connect(({ redismonitor_group, loading }) => ({
  redismonitor_group,
  loading: loading.models.redismonitor_group,
}))
@Form.create()
class MonitorGroupList extends PureComponent {
  state = {
    modalVisible: false,
    isSaveOrUpdate: true,
    selectedRows: [],
    formValues: {},
    id: null,
  };

  columns = [
    {
      title: '分组',
      dataIndex: 'group_name',
    },
    {
      title: 'Redis地址',
      dataIndex: 'ip',
    },
    {
      title: '连接客户端数',
      dataIndex: 'client_num',
    },
    {
      title: '每分钟命令数',
      dataIndex: 'commands',
      render: val => {
        if (val >=1000) {
          val /= 1000;
          return parseInt(val, 10) + " k";
        } else {
          return val;
        }
      },
    },
    {
      title: 'keys数量',
      dataIndex: 'redis_keys',
      render: val => {
        if (val >=1000) {
          val /= 1000;
          return parseInt(val, 10) + " k";
        } else {
          return val;
        }
      },
    },
    {
      title: '已用内存',
      dataIndex: 'memory_used',
      render: val => {
        if (val / 1024 / 1024 >= 1024 ) {
          val = val / 1024 / 1024 / 1024;
          return Number(val).toFixed(2) + " GB";
      } else {
          val = val / 1024 / 1024;
          return Number(val).toFixed(2) + " MB";
      }
      },
    },
    {
      title: '时间',
      dataIndex: 'create_time',
      // sorter: true,
      render: val => <span>{moment(val).format('YYYY-MM-DD HH:mm:ss')}</span>,
    },
    // {
    //   title: '操作',
    //   render: (text, record) => (
    //     <Fragment>
    //       <Popconfirm
    //         title="确定删除吗?"
    //         onConfirm={() => this.handleDelete(record)}
    //         // onCancel={cancel}
    //         okText="Yes"
    //         cancelText="No"
    //       >
    //         <a href="#">删除</a>
    //       </Popconfirm>
    //       <Divider type="vertical" />
    //       <a onClick={() => this.handleModalVisible(true, false, record)}>编辑</a>
    //     </Fragment>
    //   ),
    // },
  ];

  componentDidMount() {
    const { dispatch } = this.props;
    // dispatch({
    //   type: 'redismonitor_group/redisGroup',
    //   payload: {
    //   },
    //   callback: (res) => {
    //     if (res.status == 200 && res.data && res.data[0] && res.data[0].id) {
    //       dispatch({
    //         type: 'redismonitor_group/redisIp',
    //         payload: {
    //           groupId: res.data[0].id
    //         },
    //         callback: (res) => {
    //           console.log('ip', res);
    //           if (res.status == 200 && res.data && res.data[0] && res.data[0].id) {
    //             dispatch({
    //               type: 'redismonitor_group/fetch',
    //               payload: {
    //                 ipId: res.data[0].id
    //               },
    //             });
    //           }
    //         }
    //       });
    //     }
    //   }
    // });
    
    dispatch({
      type: 'redismonitor_group/fetch',
      payload: {
        // ipId: res.data[0].id
      },
    });
  }

  handleStandardTableChange = (pagination, filtersArg, sorter) => {
    const { dispatch } = this.props;
    const { formValues } = this.state;

    const filters = Object.keys(filtersArg).reduce((obj, key) => {
      const newObj = { ...obj };
      newObj[key] = getValue(filtersArg[key]);
      return newObj;
    }, {});

    const params = {
      current: pagination.current,
      pageSize: pagination.pageSize,
      ...formValues,
      ...filters,
    };
    if (sorter.field) {
      params.sorter = `${sorter.field}_${sorter.order}`;
    }

    dispatch({
      type: 'redismonitor_group/fetch',
      payload: params,
    });
  };

  handleFormReset = () => {
    const { form, dispatch } = this.props;
    form.resetFields();
    this.setState({
      formValues: {},
    });
    dispatch({
      type: 'redismonitor_group/fetch',
      payload: {},
    });
  };

  handleMenuClick = e => {
    const { dispatch } = this.props;
    const { selectedRows } = this.state;

    if (!selectedRows) return;
    switch (e.key) {
      case 'remove':
        dispatch({
          type: 'redismonitor_group/remove',
          payload: {
            key: selectedRows.map(row => row.key),
          },
          callback: () => {
            this.setState({
              selectedRows: [],
            });
          },
        });
        break;
      default:
        break;
    }
  };

  handleSelectRows = rows => {
    this.setState({
      selectedRows: rows,
    });
  };

  handleSearch = e => {
    e.preventDefault();

    const { dispatch, form } = this.props;

    form.validateFields((err, fieldsValue) => {
      if (err) return;
      const rangeValue = fieldsValue['range-picker'];
      const values = {
        // ...fieldsValue,
        startDate: rangeValue[0].format('YYYY-MM-DD'),
        endDate: rangeValue[1].format('YYYY-MM-DD'),
        // updatedAt: fieldsValue.updatedAt && fieldsValue.updatedAt.valueOf(),
      };
      this.setState({
        formValues: values,
      });

      dispatch({
        type: 'redismonitor_group/fetch',
        payload: values,
      });
    });
  };

  handleModalVisible = (flag, isSaveOrUpdate, fields) => {
    const { dispatch } = this.props;
    dispatch({
      type: 'redismonitor_group/redisGroup',
      payload: {
      },
    });
    if (!isSaveOrUpdate && !!fields) {// 编辑
      dispatch({
        type: 'redismonitor_group/fetchById',
        payload: {
          id: fields.id,
        },
      });
    }
    
    this.setState({
      id: fields ? fields.id : null,
      modalVisible: !!flag,
      isSaveOrUpdate: isSaveOrUpdate,
    });
  };

  handleAdd = (fields) => {
    const { dispatch } = this.props;
    dispatch({
      type: this.state.isSaveOrUpdate ? 'redismonitor_group/add' : 'redismonitor_group/update',
      payload: {
        id: this.state.id,
        ip: fields.ip,
        groupid: fields.groupid,
      },
    }).then(data => {
      dispatch({
        type: 'redismonitor_group/fetch',
      });
    });

    this.handleModalVisible();
  };

  handleDelete = fields => {
    const { dispatch } = this.props;
    dispatch({
      type: 'redismonitor_group/remove',
      payload: {
        id: fields.id,
      },
    }).then(data => {
      dispatch({
        type: 'redismonitor_group/fetch',
      });
    });

    this.handleModalVisible();
  };

  onChangeGroup = (groupId) => {
    this.props.dispatch({
      type: 'redismonitor_group/redisIp',
      payload: {
        groupId
      },
      callback: (res) => {
        // console.log('ip', res);
        // if (res.status == 200 && res.data && res.data[0] && res.data[0].id) {
        //   dispatch({
        //     type: 'redismonitor_group/fetch',
        //     payload: {
        //       ipId: res.data[0].id
        //     },
        //   });
        // }
      }
    });
  }

  renderSimpleForm() {
    const {
      redismonitor_group: { redisGroupData, redisIpData },
      form: { getFieldDecorator },
    } = this.props;
    let groupidVal = null;
    let ipVal = null;
    let groupOption = redisGroupData ? redisGroupData.map((o, i) => {
      if (i == 0) {
        groupidVal = o.id;
      }
      return <Option key={o.id} value={o.id}>{o.name}</Option>
    }) : null;
    let ipOption = redisIpData ? redisIpData.map((o, i) => {
      if (i == 0) {
        ipVal = o.id;
      }
      return <Option key={o.id} value={o.id}>{o.ip}</Option>
    }) : null;
    const rangeConfig = {
      rules: [{ type: 'array', required: true, message: 'Please select time!' }],
    };
    return (
      <Form onSubmit={this.handleSearch} layout="inline">
        <Row gutter={{ md: 8, lg: 24, xl: 48 }}>
          <Col md={8} sm={24}>
            <FormItem label="时间">
              {getFieldDecorator('range-picker', rangeConfig)(<RangePicker />)}
            </FormItem>
          </Col>
          <Col md={8} sm={24}>
            <span className={styles.submitButtons}>
              <Button type="primary" htmlType="submit">
                查询
              </Button>
              {/* <Button style={{ marginLeft: 8 }} onClick={this.handleFormReset}>
                重置
              </Button> */}
            </span>
          </Col>
        </Row>
      </Form>
    );
  }

  exportredismonitor_group = () => {
    // this.props.dispatch({
    //   type: 'redismonitor_group/exportredismonitor_group',
    // });
    let startDate = this.state.formValues.startDate ? this.state.formValues.startDate : '';
    let endDate = this.state.formValues.endDate ? this.state.formValues.endDate : '';
    location.href='/api/redis/monitor/getRedisExcel2?startDate='+startDate
    + "&endDate="+endDate;
  }

  render() {
    const {
      redismonitor_group: { data },
      loading,
    } = this.props;
    const other = {
      scroll: { x: 850, y: 300 }
    };
    let x = data.columns.length * 125 + 335;
    return (
      <PageHeaderWrapper>
        <Card bordered={false}>
          <div className={styles.tableList}>
            <div className={styles.tableListForm}>{this.renderSimpleForm()}</div>
            <div className={styles.tableListOperator}>
              <Button icon="plus" type="primary" onClick={() => this.exportredismonitor_group()}>
                下载当前Redis监控数据
              </Button>
              {/* {selectedRows.length > 0 && (
                <span>
                  <Button>批量操作</Button>
                  <Dropdown overlay={menu}>
                    <Button>
                      更多操作 <Icon type="down" />
                    </Button>
                  </Dropdown>
                </span>
              )} */}
            </div>
            <Table
              rowKey="ip_id"
              columns={data.columns} dataSource={data.list} scroll={{ x: x, y: 300 }} />
          </div>
        </Card>
      </PageHeaderWrapper>
    );
  }
}

export default MonitorGroupList;
