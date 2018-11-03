/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : knowledge

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-11-03 17:15:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `knowledge`
-- ----------------------------
DROP TABLE IF EXISTS `knowledge`;
CREATE TABLE `knowledge` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(210) DEFAULT NULL COMMENT '标题',
  `markdown` text,
  `html` text,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of knowledge
-- ----------------------------
INSERT INTO `knowledge` VALUES ('1', 'test', '    package rh.study.knowledge.controller.knowledge;\n    \n    import com.alibaba.fastjson.JSONObject;\n    import java.util.HashMap;\n    import java.util.Map;\n    import org.springframework.beans.factory.annotation.Autowired;\n    import org.springframework.web.bind.annotation.PostMapping;\n    import org.springframework.web.bind.annotation.RequestBody;\n    import org.springframework.web.bind.annotation.RequestMapping;\n    import org.springframework.web.bind.annotation.RestController;\n    import rh.study.knowledge.service.knowledge.KnowledgeService;\n    \n    /**\n     * Created by admin on 2018/11/2.\n     */\n    @RestController\n    @RequestMapping(\"/api/knowledge/\")\n    public class KnowledgeController {\n    \n      @Autowired\n      private KnowledgeService knowledgeService;\n    \n      @PostMapping(value = \"knowledge\")\n      public String saveKnowledge(\n          @RequestBody Map<String, Object> markdown\n    //      @RequestParam(value = \"markdown\") String markdown\n      ) {\n        System.out.println(markdown.get(\"markdown\"));\n        knowledgeService.save(markdown);\n    //    System.out.println(markdown);\n        Map<String, Object> map = new HashMap<>();\n        map.put(\"status\", \"ok\");\n        map.put(\"type\", \"account\");\n        map.put(\"currentAuthority\", \"admin\");\n        return JSONObject.toJSONString(map);\n      }\n    }\n    ', '    package rh.study.knowledge.controller.knowledge;\n    \n    import com.alibaba.fastjson.JSONObject;\n    import java.util.HashMap;\n    import java.util.Map;\n    import org.springframework.beans.factory.annotation.Autowired;\n    import org.springframework.web.bind.annotation.PostMapping;\n    import org.springframework.web.bind.annotation.RequestBody;\n    import org.springframework.web.bind.annotation.RequestMapping;\n    import org.springframework.web.bind.annotation.RestController;\n    import rh.study.knowledge.service.knowledge.KnowledgeService;\n    \n    /**\n     * Created by admin on 2018/11/2.\n     */\n    @RestController\n    @RequestMapping(\"/api/knowledge/\")\n    public class KnowledgeController {\n    \n      @Autowired\n      private KnowledgeService knowledgeService;\n    \n      @PostMapping(value = \"knowledge\")\n      public String saveKnowledge(\n          @RequestBody Map<String, Object> markdown\n    //      @RequestParam(value = \"markdown\") String markdown\n      ) {\n        System.out.println(markdown.get(\"markdown\"));\n        knowledgeService.save(markdown);\n    //    System.out.println(markdown);\n        Map<String, Object> map = new HashMap<>();\n        map.put(\"status\", \"ok\");\n        map.put(\"type\", \"account\");\n        map.put(\"currentAuthority\", \"admin\");\n        return JSONObject.toJSONString(map);\n      }\n    }\n    ', null, null);
INSERT INTO `knowledge` VALUES ('2', '11', '11', '<p>11</p>\n', null, null);
INSERT INTO `knowledge` VALUES ('3', 'javascript', '    <div style={{ height: \'500px\' }}>\n                  <div id=\"editormd\">\n                    {/*<textarea style={{display:\'none\'}} value={\"### Hello Editor.md !\"}></textarea>*/}\n                  </div>\n              </div>', '<pre><code>&lt;div style={{ height: \'500px\' }}&gt;\n              &lt;div id=\"editormd\"&gt;\n                {/*&lt;textarea style={{display:\'none\'}} value={\"### Hello Editor.md !\"}&gt;&lt;/textarea&gt;*/}\n              &lt;/div&gt;\n          &lt;/div&gt;\n</code></pre>', null, null);
INSERT INTO `knowledge` VALUES ('4', '11111', '# 1234', '<h1 id=\"h1-1234\"><a name=\"1234\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>1234</h1>', null, null);
INSERT INTO `knowledge` VALUES ('5', '代码块', '```\ncomponentDidMount = () => {\n    const { dispatch } = this.props;\n    dispatch({\n      type: \'editor/getMarkdown\',\n      payload: {\n        id: 4\n      }\n    });\n  }\n```', '<pre><code>componentDidMount = () =&gt; {\n    const { dispatch } = this.props;\n    dispatch({\n      type: \'editor/getMarkdown\',\n      payload: {\n        id: 4\n      }\n    });\n  }\n</code></pre>', null, null);
INSERT INTO `knowledge` VALUES ('6', '111', '# 代码块\n1. 代码1\n2. 代码2\n3. 代码3\n```\n    .markdownCss {\n      pre code {\n        display: block;\n        background: #1d1f21;\n        -webkit-border-radius: 10px;\n        -moz-border-radius: 10px;\n        border-radius: 10px;\n        margin: 20px 0px;\n        color: #c8c8c8;\n        line-height: 1.5;\n        font-size: 1.2em;\n        overflow-x: auto;\n        padding: 10px 20px;\n      }\n    }\n```\n# 代码块结束', '<h1 id=\"h1-u4EE3u7801u5757\"><a name=\"代码块\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>代码块</h1><ol>\n<li>代码1</li><li>代码2</li><li>代码3<pre><code> .markdownCss {\n   pre code {\n     display: block;\n     background: #1d1f21;\n     -webkit-border-radius: 10px;\n     -moz-border-radius: 10px;\n     border-radius: 10px;\n     margin: 20px 0px;\n     color: #c8c8c8;\n     line-height: 1.5;\n     font-size: 1.2em;\n     overflow-x: auto;\n     padding: 10px 20px;\n   }\n }\n</code></pre><h1 id=\"h1-u4EE3u7801u5757u7ED3u675F\"><a name=\"代码块结束\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>代码块结束</h1></li></ol>\n', null, null);

-- ----------------------------
-- Table structure for `test`
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of test
-- ----------------------------
INSERT INTO `test` VALUES ('1', 'test');
INSERT INTO `test` VALUES ('2', 'test');
INSERT INTO `test` VALUES ('3', 'test');
INSERT INTO `test` VALUES ('4', 'test');
INSERT INTO `test` VALUES ('5', 'test');
INSERT INTO `test` VALUES ('6', 'test');
INSERT INTO `test` VALUES ('7', 'test');
INSERT INTO `test` VALUES ('8', 'test');
INSERT INTO `test` VALUES ('9', 'test');
INSERT INTO `test` VALUES ('10', 'test');
INSERT INTO `test` VALUES ('11', 'test');
INSERT INTO `test` VALUES ('12', 'test');
INSERT INTO `test` VALUES ('13', 'test');
INSERT INTO `test` VALUES ('14', 'test');
INSERT INTO `test` VALUES ('15', 'test');
INSERT INTO `test` VALUES ('16', 'test');
INSERT INTO `test` VALUES ('17', 'test');
INSERT INTO `test` VALUES ('18', 'test');
INSERT INTO `test` VALUES ('19', 'test');
INSERT INTO `test` VALUES ('20', 'test');
INSERT INTO `test` VALUES ('21', 'test');
INSERT INTO `test` VALUES ('22', 'test');
INSERT INTO `test` VALUES ('23', 'test');
INSERT INTO `test` VALUES ('24', 'test');
INSERT INTO `test` VALUES ('25', 'test');
INSERT INTO `test` VALUES ('26', 'test');
INSERT INTO `test` VALUES ('27', 'test');
