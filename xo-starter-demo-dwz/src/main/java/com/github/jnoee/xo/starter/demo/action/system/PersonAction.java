package com.github.jnoee.xo.starter.demo.action.system;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.jnoee.xo.dwz.result.DwzResult;
import com.github.jnoee.xo.dwz.result.DwzResultBuild;
import com.github.jnoee.xo.message.MessageSource;
import com.github.jnoee.xo.starter.demo.dto.PwdChangeDto;
import com.github.jnoee.xo.starter.demo.service.UserService;

/**
 * 个人管理。
 */
@Controller
@RequestMapping("/system")
@RequiresAuthentication
public class PersonAction {
  @Resource
  private UserService userService;
  @Resource
  private MessageSource messageSource;

  /**
   * 修改个人密码。
   * 
   * @param model 数据模型
   */
  @RequestMapping("person-pwd-change")
  public void pwdChange(Model model) {
    model.addAttribute(new PwdChangeDto());
  }

  /**
   * 保存个人密码。
   * 
   * @param dto 修改密码表单dto
   * @return 返回保存个人密码提示信息。
   */
  @RequestMapping("person-pwd-change-save")
  public @ResponseBody DwzResult pwdChangeSave(PwdChangeDto dto) {
    userService.changePassword(dto.getOldPwd(), dto.getNewPwd());
    return new DwzResultBuild().success("s.person.change-pwd").closeDialog().build();
  }

  /**
   * 切换职务。
   * 
   * @param actorId 职务ID
   * @return 返回主页。
   */
  @RequestMapping("person-actor-change")
  public ModelAndView change(Long actorId) {
    userService.changeActor(actorId);
    return new ModelAndView("redirect:/index");
  }
}
