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
import YkSuccess from './ykSuccess';

import styles from './youkeList.less';

const FormItem = Form.Item;
const { Option } = Select;
const getValue = obj =>
  Object.keys(obj)
    .map(key => obj[key])
    .join(',');


/* eslint react/no-multi-comp:0 */
@connect(({ youke, loading }) => ({
  youke,
  loading: loading.models.youke,
}))
@Form.create()
class YoukeList extends PureComponent {
  state = {
    modalVisible: false,
    selectedRows: [],
    formValues: {},
    id: null,
  };

  columns = [
    {
      title: '微信标识',
      dataIndex: 'openid',
    },
    {
      title: '微信昵称',
      dataIndex: 'nickName',
    },
    {
      title: '性别',
      dataIndex: 'gender',
      render: val => {
        if (val == 2) {
          return <span style={{color: 'black'}}>女</span>
        } else if (val == 1) {
          return <span style={{color: 'black'}}>男</span>
        } else {
          return '未设置'
        }
      },
    },
    {
      title: '省份',
      dataIndex: 'province',
    },
    {
      title: '城市',
      dataIndex: 'city',
    },
    {
      title: '酒票',
      dataIndex: 'jpNum',
    },
    {
      title: '游戏次数',
      dataIndex: 'yxNum',
    },
    {
      title: '创建时间',
      dataIndex: 'createTime',
      // sorter: true,
      render: val => {return val != null ? <span>{moment(val).format('YYYY-MM-DD HH:mm:ss')}</span> : ''},
    },
    {
      title: '操作',
      render: (text, record) => {
        return record.stat == 0 ? null :
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
            <a onClick={() => this.handleModalVisible(true, record)}>我的战绩</a>
          </Fragment>
      }
    },
  ];

  componentDidMount() {
    const { dispatch } = this.props;

    dispatch({
      type: 'youke/list',
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
      type: 'youke/list',
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
      type: 'youke/list',
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
          type: 'youke/remove',
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
        type: 'youke/list',
        payload: values,
      });
    });
  };

  handleModalVisible = (flag, fields) => {
    const { dispatch } = this.props;
    
    if (!!fields) {// 我的战绩
      dispatch({
        type: 'youke/fetchYkSuccessDataById',
        payload: {
          openid: fields.openid,
        },
      });
    }
    
    this.setState({
      id: fields ? fields.openid : null,
      modalVisible: !!flag,
    });
  };

  handleAdd = (fields) => {
    // const { dispatch } = this.props;
    // dispatch({
    //   type: this.state.isSaveOrUpdate ? 'youke/add' : 'youke/update',
    //   payload: {
    //     id: this.state.id,
    //     name: fields.name,
    //     num: fields.num,
    //     phone: fields.phone,
    //   },
    // }).then(data => {
    //   dispatch({
    //     type: 'youke/list',
    //     payload: this.state.formValues,
    //   });
    // });

    this.handleModalVisible();
  };


  handleDelete = fields => {
    const { dispatch } = this.props;
    dispatch({
      type: 'youke/remove',
      payload: {
        id: fields.id,
      },
    }).then(data => {
      dispatch({
        type: 'youke/list',
        payload: this.state.formValues,
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
          <Col md={8} sm={20}>
            <FormItem label="微信昵称">
              {getFieldDecorator('nickName')(<Input placeholder="微信昵称" allowClear={true} />)}
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


  render() {
    const {
      youke: { data, ykSuccessData },
      loading,
    } = this.props;
    const { selectedRows, modalVisible, id } = this.state;
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
        <YkSuccess 
          {...parentMethods} 
          modalVisible={modalVisible} 
          id={id}
          data={ykSuccessData}
          />
      </PageHeaderWrapper>
    );
  }
}

export default YoukeList;
