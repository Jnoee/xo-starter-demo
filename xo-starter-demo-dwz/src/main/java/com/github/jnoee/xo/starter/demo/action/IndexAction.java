package com.github.jnoee.xo.starter.demo.action;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.jnoee.xo.starter.demo.service.UserService;

/**
 * 主页。
 */
@Controller
@RequestMapping("/")
@RequiresAuthentication
public class IndexAction {
  @Autowired
  private UserService userService;

  /**
   * 查看主页。
   * 
   * @param model 数据模型
   */
  @RequestMapping("index")
  public void index(Model model) {
    model.addAttribute("currentUser", userService.getLogonUser());
  }
}
