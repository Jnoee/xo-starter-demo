package com.github.jnoee.xo.starter.demo.action.system;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.jnoee.xo.dwz.result.DwzResult;
import com.github.jnoee.xo.dwz.result.DwzResultBuild;
import com.github.jnoee.xo.message.MessageSource;
import com.github.jnoee.xo.model.PageQuery;
import com.github.jnoee.xo.starter.demo.constants.AdminIds;
import com.github.jnoee.xo.starter.demo.entity.User;
import com.github.jnoee.xo.starter.demo.service.RoleService;
import com.github.jnoee.xo.starter.demo.service.UserService;

/**
 * 用户管理。
 */
@Controller
@RequestMapping("/system")
@RequiresRoles("user")
public class UserAction {
  @Resource
  private UserService userService;
  @Resource
  private RoleService roleService;
  @Resource
  private MessageSource messageSource;

  @RequestMapping("user-list")
  public void list(Model model, PageQuery query) {
    model.addAttribute("userPage", userService.search(query));
  }

  @RequestMapping("user-add")
  public void add(Model model) {
    model.addAttribute("user", new User());
    model.addAttribute("rootOrgan", userService.getCurrentOrgan());
    model.addAttribute("roles", roleService.getAll());
  }

  @RequestMapping("user-save")
  public @ResponseBody DwzResult save(User user) {
    userService.create(user);
    return new DwzResultBuild().success("s.user.add").closeDialog().reloadNavTab().build();
  }

  @RequestMapping("user-edit")
  public void edit(Model model, User user) {
    model.addAttribute(user);
  }

  @RequestMapping("user-update")
  public @ResponseBody DwzResult update(User user) {
    userService.update(user);
    return new DwzResultBuild().success("s.user.edit").closeDialog().reloadNavTab().build();
  }

  @RequestMapping("user-disable")
  public @ResponseBody DwzResult disable(User user) {
    userService.disable(user);
    return new DwzResultBuild().success("s.user.disable").reloadNavTab().build();
  }

  @RequestMapping("user-enable")
  public @ResponseBody DwzResult enable(User user) {
    userService.enable(user);
    return new DwzResultBuild().success("s.user.enable").reloadNavTab().build();
  }

  /**
   * 重置用户密码。
   * 
   * @param model 数据模型
   * @param user 用户
   */
  @RequestMapping("user-pwd-reset")
  public void pwdReset(Model model, User user) {
    model.addAttribute("defaultPassword", AdminIds.NEW_SALT);
  }

  /**
   * 保存重置密码。
   * 
   * @param managePassword 管理员密码
   * @param user 用户
   * @return 返回提示信息。
   */
  @RequestMapping("user-pwd-reset-save")
  public @ResponseBody DwzResult pwdResetSave(String managePassword, User user) {
    userService.resetPassword(managePassword, user);
    return new DwzResultBuild().success("s.user.reset-pwd").closeDialog().build();
  }
}
