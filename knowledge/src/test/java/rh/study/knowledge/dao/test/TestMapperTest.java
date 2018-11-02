package rh.study.knowledge.dao.test;

import static org.junit.Assert.*;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by admin on 2018/11/2.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMapperTest {

  @Autowired
  private TestMapper testMapper;

  @Test
  public void findByName() throws Exception {
    final int row1 = testMapper.insertSelective(new rh.study.knowledge.entity.test.Test("test"));
    testMapper.insert(new rh.study.knowledge.entity.test.Test("test"));
    testMapper.insert(new rh.study.knowledge.entity.test.Test("test"));
    testMapper.insert(new rh.study.knowledge.entity.test.Test("test"));
    testMapper.insert(new rh.study.knowledge.entity.test.Test("test"));
    testMapper.insert(new rh.study.knowledge.entity.test.Test("test"));
    testMapper.insert(new rh.study.knowledge.entity.test.Test("test"));
    testMapper.insert(new rh.study.knowledge.entity.test.Test("test"));
    testMapper.insert(new rh.study.knowledge.entity.test.Test("test"));
    testMapper.insert(new rh.study.knowledge.entity.test.Test("test"));
    testMapper.insert(new rh.study.knowledge.entity.test.Test("test"));
    testMapper.insert(new rh.study.knowledge.entity.test.Test("test"));
    testMapper.insert(new rh.study.knowledge.entity.test.Test("test"));
    System.out.println(row1);
    final List<rh.study.knowledge.entity.test.Test> list = testMapper.findByName("test");
    System.out.println(list.get(0).getName());
    // TODO 分页 + 排序 this.userMapper.selectAll() 这一句就是我们需要写的查询，有了这两款插件无缝切换各种数据库
    final PageInfo<Object> pageInfo = PageHelper.startPage(1, 10).setOrderBy("id desc").doSelectPageInfo(() -> this.testMapper.selectAll());
    System.out.println(pageInfo.toString());
    PageHelper.startPage(1, 10).setOrderBy("id desc");
    final PageInfo<rh.study.knowledge.entity.test.Test> userPageInfo = new PageInfo<>(this.testMapper.selectAll());
    System.out.println(userPageInfo);
  }

}