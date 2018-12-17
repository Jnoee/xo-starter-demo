package com.github.jnoee.xo.starter.demo.action.system;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统管理菜单。
 */
@Controller("system.menu")
@RequestMapping("/system")
@RequiresRoles(value = {"organ", "role", "user", "log"}, logical = Logical.OR)
public class MenuAction {
  @RequestMapping("menu")
  public void menu() {}
}
