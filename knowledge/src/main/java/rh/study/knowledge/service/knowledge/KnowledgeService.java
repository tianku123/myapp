package rh.study.knowledge.service.knowledge;

import java.util.Map;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rh.study.knowledge.dao.knowledge.KnowledgeMapper;
import rh.study.knowledge.entity.knowledge.Knowledge;

/**
 * Created by admin on 2018/11/2.
 */
@Service
public class KnowledgeService {

  @Autowired
  private KnowledgeMapper knowledgeMapper;

  public int save(Map<String, Object> map) {
    String title = MapUtils.getString(map, "title");
    String markdown = MapUtils.getString(map, "markdown");
    String html = MapUtils.getString(map, "html");
    Knowledge bean = new Knowledge();
    bean.setTitle(title);
    bean.setMarkdown(markdown);
    bean.setHtml(html);
    return knowledgeMapper.insertSelective(bean);
  }

  public Knowledge getKnowledgeById(Long id) {
    return knowledgeMapper.selectByPrimaryKey(id);
  }
}
