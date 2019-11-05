import React, { PureComponent, Fragment } from 'react';
import { Table, Alert } from 'antd';
import styles from './index.less';

class StandardTable extends PureComponent {
  constructor(props) {
    super(props);
    const { columns } = props;
    
    this.state = {
      selectedRowKeys: [],
    };
  }

  // 替换 componentWillReceiveProps 方法
  static getDerivedStateFromProps(nextProps) {
    // clean state
    if (nextProps.selectedRows.length === 0) {
      return {
        selectedRowKeys: [],
      };
    }
    return null;
  }

  handleRowSelectChange = (selectedRowKeys, selectedRows) => {
    const { onSelectRow } = this.props;
    if (onSelectRow) {
      onSelectRow(selectedRows);
    }

    this.setState({ selectedRowKeys });
  };

  handleTableChange = (pagination, filters, sorter) => {
    const { onChange } = this.props;
    if (onChange) {
      onChange(pagination, filters, sorter);
    }
  };

  cleanSelectedKeys = () => {
    this.handleRowSelectChange([], []);
  };

  render() {
    const { selectedRowKeys } = this.state;
    const {
      data: { list, pagination },
      loading,
      columns,
      rowKey,
    } = this.props;

    const paginationProps = {
      showSizeChanger: true,
      showQuickJumper: true,
      ...pagination,
    };

    const rowSelection = {
      selectedRowKeys,
      onChange: this.handleRowSelectChange,
      getCheckboxProps: record => ({
        disabled: record.disabled,
      }),
    };

    return (
      <div className={styles.standardTable}>
        {/* <div className={styles.tableAlert}>
          <Alert
            message={
              <Fragment>
                已选择 <a style={{ fontWeight: 600 }}>{selectedRowKeys.length}</a> 项&nbsp;&nbsp;
                <a onClick={this.cleanSelectedKeys} style={{ marginLeft: 24 }}>
                  清空
                </a>
              </Fragment>
            }
            type="info"
            showIcon
          />
        </div> */}
        <Table
          loading={loading}
          rowKey={rowKey || 'key'}
          rowSelection={rowSelection}
          dataSource={list}
          columns={columns}
          pagination={paginationProps}
          onChange={this.handleTableChange}
        />
      </div>
    );
  }
}

export default StandardTable;
