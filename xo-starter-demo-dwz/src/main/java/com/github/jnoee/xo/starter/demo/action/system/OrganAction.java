package com.github.jnoee.xo.starter.demo.action.system;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.jnoee.xo.dwz.result.DwzResult;
import com.github.jnoee.xo.dwz.result.DwzResultBuild;
import com.github.jnoee.xo.message.MessageSource;
import com.github.jnoee.xo.starter.demo.entity.Organ;
import com.github.jnoee.xo.starter.demo.service.OrganService;
import com.github.jnoee.xo.starter.demo.service.UserService;

/**
 * 机构管理
 */
@Controller("system.organ")
@RequestMapping("/system")
@RequiresRoles("organ")
public class OrganAction {
  @Resource
  private OrganService organService;
  @Resource
  private UserService userService;
  @Resource
  private MessageSource messageSource;

  @RequestMapping("organ-list")
  public void list(Model model, String selectedOrganId) {
    Organ rootOrgan = organService.getRoot();
    if (selectedOrganId == null) {
      selectedOrganId = rootOrgan.getId();
    }
    model.addAttribute("selectedOrganId", selectedOrganId);
    model.addAttribute("rootOrgan", rootOrgan);
  }

  @RequestMapping("organ-add")
  public void add(Model model) {
    Organ rootOrgan = organService.getRoot();
    Organ organ = new Organ();
    organ.setParent(rootOrgan);
    model.addAttribute("parentOrgans", rootOrgan.getChildTree());
    model.addAttribute("rootOrgan", rootOrgan);
    model.addAttribute(organ);
  }

  @RequestMapping("organ-save")
  public @ResponseBody DwzResult save(Organ organ) {
    organService.create(organ);
    return new DwzResultBuild().success("s.organ.add").closeDialog()
        .reloadNavTab("selectedOrganId=" + organ.getId()).build();
  }

  @RequestMapping("organ-edit")
  public void edit(Model model, Organ organ) {
    Organ rootOrgan = organService.getRoot();
    model.addAttribute("rootOrgan", rootOrgan);
    List<Organ> parentOrgans = rootOrgan.getChildTree();
    parentOrgans.remove(organ);
    model.addAttribute("parentOrgans", parentOrgans);
  }

  @RequestMapping("organ-update")
  public @ResponseBody DwzResult update(Organ organ) {
    organService.update(organ);
    return new DwzResultBuild().success("s.organ.edit")
        .reloadNavTab("selectedOrganId=" + organ.getId()).build();
  }

  @RequestMapping("organ-delete")
  public @ResponseBody DwzResult delete(Organ organ) {
    organService.delete(organ);
    return new DwzResultBuild().success("s.organ.del").closeDialog()
        .reloadNavTab("selectedOrganId=" + userService.getCurrentOrgan().getId()).build();
  }

  @RequestMapping("organ-enable")
  public @ResponseBody DwzResult enable(Organ organ) {
    organService.enable(organ);
    return new DwzResultBuild().success("s.organ.enable")
        .reloadNavTab("selectedOrganId=" + organ.getId()).build();
  }

  @RequestMapping("organ-disable")
  public @ResponseBody DwzResult disable(Organ organ) {
    organService.disable(organ);
    return new DwzResultBuild().success("s.organ.disable")
        .reloadNavTab("selectedOrganId=" + organ.getId()).build();
  }
}
