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
  Popconfirm,
  Divider,
} from 'antd';
import StandardTable from '@/components/StandardTable';
import PageHeaderWrapper from '@/components/PageHeaderWrapper';

import styles from './RedisIpList.less';

const FormItem = Form.Item;
const { Option } = Select;
const getValue = obj =>
  Object.keys(obj)
    .map(key => obj[key])
    .join(',');


/* eslint react/no-multi-comp:0 */
@connect(({ redismonitor, loading }) => ({
  redismonitor,
  loading: loading.models.redismonitor,
}))
@Form.create()
class MonitorList extends PureComponent {
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
      dataIndex: 'name',
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
    //   type: 'redismonitor/fetch',
    // });
    dispatch({
      type: 'redismonitor/redisGroup',
      payload: {
      },
      callback: (res) => {
        if (res.status == 200 && res.data && res.data[0] && res.data[0].id) {
          dispatch({
            type: 'redismonitor/redisIp',
            payload: {
              groupId: res.data[0].id
            },
            callback: (res) => {
              console.log('ip', res);
              if (res.status == 200 && res.data && res.data[0] && res.data[0].id) {
                dispatch({
                  type: 'redismonitor/fetch',
                  payload: {
                    ipId: res.data[0].id
                  },
                });
              }
            }
          });
        }
      }
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
      type: 'redismonitor/fetch',
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
      type: 'redismonitor/fetch',
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
          type: 'redismonitor/remove',
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

      const values = {
        ...fieldsValue,
        updatedAt: fieldsValue.updatedAt && fieldsValue.updatedAt.valueOf(),
      };

      this.setState({
        formValues: values,
      });

      dispatch({
        type: 'redismonitor/fetch',
        payload: values,
      });
    });
  };

  handleModalVisible = (flag, isSaveOrUpdate, fields) => {
    const { dispatch } = this.props;
    dispatch({
      type: 'redismonitor/redisGroup',
      payload: {
      },
    });
    if (!isSaveOrUpdate && !!fields) {// 编辑
      dispatch({
        type: 'redismonitor/fetchById',
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
      type: this.state.isSaveOrUpdate ? 'redismonitor/add' : 'redismonitor/update',
      payload: {
        id: this.state.id,
        ip: fields.ip,
        groupid: fields.groupid,
      },
    }).then(data => {
      dispatch({
        type: 'redismonitor/fetch',
      });
    });

    this.handleModalVisible();
  };

  handleDelete = fields => {
    const { dispatch } = this.props;
    dispatch({
      type: 'redismonitor/remove',
      payload: {
        id: fields.id,
      },
    }).then(data => {
      dispatch({
        type: 'redismonitor/fetch',
      });
    });

    this.handleModalVisible();
  };

  onChangeGroup = (groupId) => {
    this.props.dispatch({
      type: 'redismonitor/redisIp',
      payload: {
        groupId
      },
      callback: (res) => {
        // console.log('ip', res);
        // if (res.status == 200 && res.data && res.data[0] && res.data[0].id) {
        //   dispatch({
        //     type: 'redismonitor/fetch',
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
      redismonitor: { redisGroupData, redisIpData },
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
    return (
      <Form onSubmit={this.handleSearch} layout="inline">
        <Row gutter={{ md: 8, lg: 24, xl: 48 }}>
          <Col md={8} sm={24}>
            <FormItem label="分组">
            {getFieldDecorator('groupid', { initialValue: groupidVal,
            })(
              <Select style={{ width: '100%' }} showSearch={true}
                onChange={this.onChangeGroup}
              >
                {groupOption}
              </Select>)}
            </FormItem>
          </Col>
          <Col md={8} sm={24}>
          <FormItem label="Redis地址">
            {getFieldDecorator('ipId', { initialValue: ipVal,
            })(<Select style={{ width: '100%' }}>
            {ipOption}
            </Select>)}
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

  exportRedisMonitor = () => {
    // this.props.dispatch({
    //   type: 'redismonitor/exportRedisMonitor',
    // });
    location.href='/api/redis/monitor/getRedisExcel';
  }

  render() {
    const {
      redismonitor: { data },
      loading,
    } = this.props;
    const { selectedRows, modalVisible, isSaveOrUpdate, id } = this.state;
    const menu = (
      <Menu onClick={this.handleMenuClick} selectedKeys={[]}>
        <Menu.Item key="remove">删除</Menu.Item>
        <Menu.Item key="approval">批量审批</Menu.Item>
      </Menu>
    );

    const parentMethods = {
      handleAdd: this.handleAdd,
      handleModalVisible: this.handleModalVisible,
    };
    
    return (
      <PageHeaderWrapper>
        <Card bordered={false}>
          <div className={styles.tableList}>
            <div className={styles.tableListForm}>{this.renderSimpleForm()}</div>
            <div className={styles.tableListOperator}>
              {/* <Button icon="plus" type="primary" onClick={() => this.handleModalVisible(true, true)}>
                新建
              </Button>
              <Button icon="plus" type="primary" onClick={() => this.exportRedisMonitor()}>
                下载当前Redis监控数据
              </Button> */}
              {selectedRows.length > 0 && (
                <span>
                  <Button>批量操作</Button>
                  <Dropdown overlay={menu}>
                    <Button>
                      更多操作 <Icon type="down" />
                    </Button>
                  </Dropdown>
                </span>
              )}
            </div>
            <StandardTable
              selectedRows={selectedRows}
              loading={loading}
              data={data}
              rowKey={record => record.id}
              columns={this.columns}
              onSelectRow={this.handleSelectRows}
              onChange={this.handleStandardTableChange}
            />
          </div>
        </Card>
      </PageHeaderWrapper>
    );
  }
}

export default MonitorList;
