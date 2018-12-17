package com.github.jnoee.xo.demo.action.system;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.jnoee.xo.demo.entity.Role;
import com.github.jnoee.xo.demo.service.RoleService;
import com.github.jnoee.xo.dwz.result.DwzResult;
import com.github.jnoee.xo.dwz.result.DwzResultBuild;
import com.github.jnoee.xo.message.MessageSource;
import com.github.jnoee.xo.privileg.PrivilegManager;
import com.github.jnoee.xo.utils.CollectionUtils;

/**
 * 角色管理。
 */
@Controller
@RequestMapping("/system")
@RequiresRoles("role")
public class RoleAction {
  @Resource
  private RoleService roleService;
  @Resource
  private PrivilegManager privilegManager;
  @Resource
  private MessageSource messageSource;

  @RequestMapping("role-list")
  public void list(Model model, String selectedRoleId) {
    List<Role> roles = roleService.getAll();
    if (selectedRoleId == null) {
      selectedRoleId = roles.get(0).getId();
    }
    model.addAttribute("selectedRoleId", selectedRoleId);
    model.addAttribute("roles", roles);
  }

  @RequestMapping("role-add")
  public void add(Model model) {
    model.addAttribute(new Role());
    model.addAttribute("privilegs", new ArrayList<String>());
    model.addAttribute("allPrivilegs", privilegManager.getPrivilegs());
  }

  @RequestMapping("role-save")
  public @ResponseBody DwzResult save(Role role, String[] privilegs) {
    role.setPrivilegs(CollectionUtils.toList(privilegs));
    roleService.create(role);
    return new DwzResultBuild().success("s.role.add").closeDialog()
        .reloadNavTab("selectedRoleId=" + role.getId()).build();
  }

  @RequestMapping("role-edit")
  public void edit(Role role, Model model) {
    model.addAttribute("privilegs", role.getPrivilegs());
    model.addAttribute("allPrivilegs", privilegManager.getPrivilegs());
  }

  @RequestMapping("role-update")
  public @ResponseBody DwzResult update(Role role, String[] privilegs) {
    role.setPrivilegs(CollectionUtils.toList(privilegs));
    roleService.update(role);
    return new DwzResultBuild().success("s.role.edit")
        .reloadNavTab("selectedRoleId=" + role.getId()).build();
  }
}
