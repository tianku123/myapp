import React, { createElement } from 'react';
import classNames from 'classnames';
import { Button } from 'antd';
import config from './typeConfig';
import styles from './index.less';

class Exception extends React.PureComponent {
  static defaultProps = {
    backText: 'back to home',
    redirect: '/',
  };

  constructor(props) {
    super(props);
    this.state = {};
  }

  componentDidMount () {
    $(function() {
      var editor = editormd("editormd", {
        path : "/editor.md/lib/" // Autoload modules mode, codemirror, marked... dependents libs path
      });

      /*
      // or
      var editor = editormd({
          id   : "editormd",
          path : "../lib/"
      });
      */
    });

  }

  render() {
    const {
      className,
      backText,
      linkElement = 'a',
      type,
      title,
      desc,
      img,
      actions,
      redirect,
      ...rest
    } = this.props;
    const pageType = type in config ? type : '404';
    const clsString = classNames(styles.exception, className);
    return (
      <div className={clsString} {...rest}>
        <div id="editormd">
          <textarea style={{display:'none'}}>### Hello Editor.md !</textarea>
        </div>
      </div>
    );
  }
}

export default Exception;
