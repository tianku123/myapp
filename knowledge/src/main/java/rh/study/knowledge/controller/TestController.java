package rh.study.knowledge.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by admin on 2018/10/25.
 */
@RestController
@RequestMapping("/api/test/")
public class TestController {

  @GetMapping("/test")
  public String test() {
    return "hello springboot";
  }
}
