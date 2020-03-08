package rh.study.knowledge.controller.auth;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2018/10/25.
 */
@RestController
@RequestMapping("/api/login/")
public class LoginController {

  @Value("${ht_username}")
  private String username;
  @Value("${ht_password}")
  private String password;

  @PostMapping("/account")
  public String test(
      @RequestParam() String userName,
      @RequestParam() String password,
      @RequestParam() String type
  ) {
    Map<String, Object> map = new HashMap<>();
    if (this.username.equals(userName) && this.password.equals(password)) {
      map.put("status", "ok");
      map.put("type", "account");
      map.put("currentAuthority", "admin");
    } else {
      map.put("status", "error");
      map.put("type", "account");
      map.put("currentAuthority", "guest");
    }
    return JSONObject.toJSONString(map);
  }
}
