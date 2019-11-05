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

import styles from './RedisGroupList.less';
import RedisGroupForm from './RedisGroupForm';

const FormItem = Form.Item;
const { Option } = Select;
const getValue = obj =>
  Object.keys(obj)
    .map(key => obj[key])
    .join(',');

/* eslint react/no-multi-comp:0 */
@connect(({ redisgroup, loading }) => ({
  redisgroup,
  loading: loading.models.redisgroup,
}))
@Form.create()
class RedisGroupList extends PureComponent {
  state = {
    modalVisible: false,
    isSaveOrUpdate: true,
    selectedRows: [],
    formValues: {},
    id: null,
  };

  columns = [
    {
      title: '名称',
      dataIndex: 'name',
    },
    // {
    //   title: '排序',
    //   dataIndex: 'ord',
    // },
    {
      title: '状态',
      dataIndex: 'stat',
      render: val => {
        if (val == 0 || val == null) {
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
      type: 'redisgroup/fetch',
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
      type: 'redisgroup/fetch',
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
      type: 'redisgroup/fetch',
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
          type: 'redisgroup/remove',
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
        type: 'redisgroup/fetch',
        payload: values,
      });
    });
  };

  handleModalVisible = (flag, isSaveOrUpdate, fields) => {
    if (!isSaveOrUpdate && !!fields) {// 编辑
      const { dispatch } = this.props;
      dispatch({
        type: 'redisgroup/fetchById',
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
      type: this.state.isSaveOrUpdate ? 'redisgroup/add' : 'redisgroup/update',
      payload: {
        id: this.state.id,
        name: fields.name,
      },
    }).then(data => {
      console.log('add', data);
      dispatch({
        type: 'redisgroup/fetch',
      });
    });

    this.handleModalVisible();
    
  };

  handleDelete = fields => {
    const { dispatch } = this.props;
    dispatch({
      type: 'redisgroup/remove',
      payload: {
        id: fields.id,
      },
    }).then(data => {
      dispatch({
        type: 'redisgroup/fetch',
      });
    });

    this.handleModalVisible();
    
  };

  renderSimpleForm() {
    const {
      form: { getFieldDecorator },
    } = this.props;
    return (
      <Form onSubmit={this.handleSearch} layout="inline">
        <Row gutter={{ md: 8, lg: 24, xl: 48 }}>
          <Col md={8} sm={24}>
            <FormItem label="规则名称">
              {getFieldDecorator('name')(<Input placeholder="请输入" />)}
            </FormItem>
          </Col>
          <Col md={8} sm={24}>
            <FormItem label="使用状态">
              {getFieldDecorator('status')(
                <Select placeholder="请选择" style={{ width: '100%' }}>
                  <Option value="0">关闭</Option>
                  <Option value="1">运行中</Option>
                </Select>
              )}
            </FormItem>
          </Col>
          <Col md={8} sm={24}>
            <span className={styles.submitButtons}>
              <Button type="primary" htmlType="submit">
                查询
              </Button>
              <Button style={{ marginLeft: 8 }} onClick={this.handleFormReset}>
                重置
              </Button>
            </span>
          </Col>
        </Row>
      </Form>
    );
  }

  render() {
    const {
      redisgroup: { data, fetchByIdData },
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
      <PageHeaderWrapper >
        <Card bordered={false}>
          <div className={styles.tableList}>
            {/* <div className={styles.tableListForm}>{this.renderSimpleForm()}</div> */}
            <div className={styles.tableListOperator}>
              <Button icon="plus" type="primary" onClick={() => this.handleModalVisible(true, true)}>
                新建
              </Button>
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
        <RedisGroupForm 
          {...parentMethods} 
          modalVisible={modalVisible} 
          isSaveOrUpdate={isSaveOrUpdate} 
          id={id}
          data={fetchByIdData}
          />
      </PageHeaderWrapper>
    );
  }
}

export default RedisGroupList;
