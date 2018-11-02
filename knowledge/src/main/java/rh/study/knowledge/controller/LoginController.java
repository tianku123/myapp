package rh.study.knowledge.controller;

import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by admin on 2018/10/25.
 */
@RestController
@RequestMapping("/api/login/")
public class LoginController {

  @GetMapping("/account")
  public String test(
      @RequestParam() String userName,
      @RequestParam() String password,
      @RequestParam() String type
  ) {
    Map<String, Object> map = new HashMap<>();
    map.put("status", "ok");
    map.put("type", "account");
    map.put("currentAuthority", "admin");
    return JSONObject.toJSONString(map);
  }
}
