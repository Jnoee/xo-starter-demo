package com.github.jnoee.xo.starter.demo.action;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页。
 */
@Controller
@RequestMapping("/")
@RequiresAuthentication
public class HomeAction {
  /**
   * 查看首页。
   */
  @RequestMapping("home")
  public void home() {}
}
