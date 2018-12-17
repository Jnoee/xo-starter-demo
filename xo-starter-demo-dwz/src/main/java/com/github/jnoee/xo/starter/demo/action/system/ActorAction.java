package com.github.jnoee.xo.starter.demo.action.system;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.jnoee.xo.dwz.result.DwzResult;
import com.github.jnoee.xo.dwz.result.DwzResultBuild;
import com.github.jnoee.xo.starter.demo.entity.Actor;
import com.github.jnoee.xo.starter.demo.entity.User;
import com.github.jnoee.xo.starter.demo.service.ActorService;
import com.github.jnoee.xo.starter.demo.service.RoleService;
import com.github.jnoee.xo.starter.demo.service.UserService;

/**
 * 职务管理。
 */
@Controller
@RequestMapping("/system")
@RequiresRoles("user")
public class ActorAction {
  @Resource
  private ActorService actorService;
  @Resource
  private RoleService roleService;
  @Resource
  private UserService userService;

  @RequestMapping("actor-list")
  public void list(Model model, User user) {
    model.addAttribute(user);
  }

  @RequestMapping("actor-add")
  public void add(Model model, User user) {
    Actor actor = new Actor();
    actor.setUser(user);
    model.addAttribute(actor);
    model.addAttribute("rootOrgan", userService.getCurrentOrgan());
    model.addAttribute("roles", roleService.getAll());
  }

  @RequestMapping("actor-save")
  public @ResponseBody DwzResult save(Actor actor) {
    actorService.create(actor);
    return new DwzResultBuild().success("s.actor.add").closeDialog()
        .reloadDialog("actor-list", "", "").build();
  }

  @RequestMapping("actor-edit")
  public void edit(Model model, Actor actor) {
    model.addAttribute("rootOrgan", userService.getLogonUser().getDefaultActor().getOrgan());
    model.addAttribute("roles", roleService.getAll());
  }

  @RequestMapping("actor-update")
  public @ResponseBody DwzResult update(Actor actor) {
    actorService.update(actor);
    return new DwzResultBuild().success("s.actor.edit").closeDialog()
        .reloadDialog("actor-list", "", "").reloadNavTab().build();
  }

  @RequestMapping("actor-delete")
  public @ResponseBody DwzResult delete(Actor actor) {
    actorService.delete(actor);
    return new DwzResultBuild().success("s.actor.del").reloadDialog().build();
  }

  /**
   * 设置默认职务。
   * 
   * @param actor 职务
   * @return 返回提示信息。
   */
  @RequestMapping("actor-set-default")
  public @ResponseBody DwzResult setDefault(Actor actor) {
    actorService.setDefault(actor);
    return new DwzResultBuild().success("s.actor.set-default").reloadDialog().reloadNavTab()
        .build();
  }
}
