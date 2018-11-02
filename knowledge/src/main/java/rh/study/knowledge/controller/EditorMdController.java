package rh.study.knowledge.controller;

import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by admin on 2018/10/25.
 */
@RestController
@RequestMapping("/api/editor/")
public class EditorMdController {

  @PostMapping(value = "saveMarkdown")
  public String saveMarkdown(
      @RequestBody Map<String, Object> markdown
//      @RequestParam(value = "markdown") String markdown
  ) {
    System.out.println(markdown.get("markdown"));
//    System.out.println(markdown);
    Map<String, Object> map = new HashMap<>();
    map.put("status", "ok");
    map.put("type", "account");
    map.put("currentAuthority", "admin");
    return JSONObject.toJSONString(map);
  }

  @DeleteMapping(value = "deleteMarkdown")
  public String deleteMarkdown(
      @RequestBody Map<String, Object> markdown
//      @RequestParam(value = "markdown") String markdown
  ) {
    System.out.println(markdown.get("markdown"));
//    System.out.println(markdown);
    Map<String, Object> map = new HashMap<>();
    map.put("status", "ok");
    map.put("type", "account");
    map.put("currentAuthority", "admin");
    return JSONObject.toJSONString(map);
  }

  @DeleteMapping(value = "deleteMarkdown2")
  public String deleteMarkdown2(
//      @RequestBody Map<String, Object> markdown
      @RequestParam(value = "markdown") String markdown
  ) {
//    System.out.println(markdown.get("markdown"));
    System.out.println(markdown);
    Map<String, Object> map = new HashMap<>();
    map.put("status", "ok");
    map.put("type", "account");
    map.put("currentAuthority", "admin");
    return JSONObject.toJSONString(map);
  }
}
