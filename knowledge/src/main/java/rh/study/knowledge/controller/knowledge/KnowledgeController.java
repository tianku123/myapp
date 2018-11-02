package rh.study.knowledge.controller.knowledge;

import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rh.study.knowledge.entity.knowledge.Knowledge;
import rh.study.knowledge.service.knowledge.KnowledgeService;

/**
 * Created by admin on 2018/11/2.
 */
@RestController
@RequestMapping("/api/knowledge/")
public class KnowledgeController {

  @Autowired
  private KnowledgeService knowledgeService;

  @PostMapping(value = "knowledge")
  public String saveKnowledge(
      @RequestBody Map<String, Object> markdown
//      @RequestParam(value = "markdown") String markdown
  ) {
    System.out.println(markdown.get("markdown"));
    knowledgeService.save(markdown);
//    System.out.println(markdown);
    Map<String, Object> map = new HashMap<>();
    map.put("status", "ok");
    map.put("type", "account");
    map.put("currentAuthority", "admin");
    return JSONObject.toJSONString(map);
  }

  @GetMapping(value = "knowledge/{id}")
  public String getKnowledgeById(
      @PathVariable  Long id
  ) {
    Knowledge bean = knowledgeService.getKnowledgeById(id);
//    System.out.println(markdown);
    Map<String, Object> map = new HashMap<>();
    map.put("status", "ok");
    map.put("type", "account");
    map.put("currentAuthority", "admin");
    return JSONObject.toJSONString(bean);
  }
}
