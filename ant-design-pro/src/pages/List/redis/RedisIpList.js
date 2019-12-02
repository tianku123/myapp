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
import RedisIpForm from './RedisIpForm';

const FormItem = Form.Item;
const { Option } = Select;
const getValue = obj =>
  Object.keys(obj)
    .map(key => obj[key])
    .join(',');


/* eslint react/no-multi-comp:0 */
@connect(({ redisip, loading }) => ({
  redisip,
  loading: loading.models.redisip,
}))
@Form.create()
class RedisIpList extends PureComponent {
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
      title: '状态',
      dataIndex: 'stat',
      render: val => {
        if (val == 0) {
          return "正常";
        } else if (val == 1) {
          return <span style={{color: 'red'}}>删除</span>
        }
      },
    },
    {
      title: '说明',
      dataIndex: 'intro',
    },
    {
      title: '创建时间',
      dataIndex: 'createTime',
      // sorter: true,
      render: val => {return val != null ? <span>{moment(val).format('YYYY-MM-DD HH:mm:ss')}</span> : ''},
    },
    {
      title: '更新时间',
      dataIndex: 'updateTime',
      // sorter: true,
    render: val => {return val != null ? <span>{moment(val).format('YYYY-MM-DD HH:mm:ss')}</span> : ''},
    },
    {
      title: '操作',
      render: (text, record) => {
        return record.stat == 1 ? null :
          <Fragment>
            <Popconfirm
              title="确定删除吗?"
              onConfirm={() => this.handleDelete(record)}
              // onCancel={cancel}
              okText="是"
              cancelText="否"
            >
              <a href="#">删除</a>
            </Popconfirm>
            <Divider type="vertical" />
            <a onClick={() => this.handleModalVisible(true, false, record)}>编辑</a>
          </Fragment>
      }
    },
  ];

  componentDidMount() {
    const { dispatch } = this.props;

    dispatch({
      type: 'redisip/redisGroup',
      payload: {
      },
      callback: (res) => {
        // if (res.status == 200 && res.data && res.data[0] && res.data[0].id) {
          // dispatch({
          //   type: 'redisip/redisIp',
          //   payload: {
          //     groupId: res.data[0].id
          //   },
          //   callback: (res) => {
              
              dispatch({
                type: 'redisip/fetch',
              });
            // }
          // });
        // }
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
      type: 'redisip/fetch',
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
      type: 'redisip/fetch',
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
          type: 'redisip/remove',
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
      };

      this.setState({
        formValues: values,
      });

      dispatch({
        type: 'redisip/fetch',
        payload: values,
      });
    });
  };

  handleModalVisible = (flag, isSaveOrUpdate, fields) => {
    const { dispatch } = this.props;
    dispatch({
      type: 'redisip/redisGroup',
      payload: {
      },
    });
    if (!isSaveOrUpdate && !!fields) {// 编辑
      dispatch({
        type: 'redisip/fetchById',
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
      type: this.state.isSaveOrUpdate ? 'redisip/add' : 'redisip/update',
      payload: {
        id: this.state.id,
        ip: fields.ip,
        groupid: fields.groupid,
      },
    }).then(data => {
      dispatch({
        type: 'redisip/fetch',
        payload: this.state.formValues,
      });
    });

    this.handleModalVisible();
  };

  handleDelete = fields => {
    const { dispatch } = this.props;
    dispatch({
      type: 'redisip/remove',
      payload: {
        id: fields.id,
      },
    }).then(data => {
      dispatch({
        type: 'redisip/fetch',
        payload: this.state.formValues,
      });
    });

    this.handleModalVisible();
  };

  renderSimpleForm() {
    const {
      redisip: { redisGroupData, redisIpData },
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
          <Col md={6} sm={18}>
            <FormItem label="分组">
            {getFieldDecorator('groupId', {
            })(
              <Select style={{ width: '100%' }} showSearch={true} allowClear={true}
              >
                {groupOption}
              </Select>)}
            </FormItem>
          </Col>
          <Col md={8} sm={20}>
            <FormItem label="Redis地址">
              {getFieldDecorator('ip')(<Input placeholder="请输入IP" allowClear={true} />)}
            </FormItem>
          </Col>
          <Col md={4} sm={12}>
            <FormItem label="状态">
            {getFieldDecorator('stat', {
            })(
              <Select style={{ width: '100%' }} showSearch={true} allowClear={true}
              >
                <Option key="0" value="0">正常</Option>
                <Option key="1" value="1">删除</Option>
              </Select>)}
            </FormItem>
          </Col>
          <Col md={4} sm={12}>
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
    //   type: 'redisip/exportRedisMonitor',
    // });
    location.href='/api/redis/monitor/getRedisExcel';
  }

  render() {
    const {
      redisip: { data, fetchByIdData, redisGroupData },
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
              <Button icon="plus" type="primary" onClick={() => this.handleModalVisible(true, true)}>
                新建
              </Button>
              {/* <Button icon="plus" type="primary" onClick={() => this.exportRedisMonitor()}>
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
        <RedisIpForm 
          {...parentMethods} 
          modalVisible={modalVisible} 
          isSaveOrUpdate={isSaveOrUpdate} 
          id={id}
          data={fetchByIdData}
          redisGroupData={redisGroupData}
          />
      </PageHeaderWrapper>
    );
  }
}

export default RedisIpList;
