package com.github.jnoee.xo.starter.demo.api;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.jnoee.xo.starter.demo.dto.PwdChangeDto;
import com.github.jnoee.xo.starter.demo.service.UserService;
import com.github.jnoee.xo.starter.demo.vo.ActorVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "/person", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = "个人")
@RequiresAuthentication
public class PersonApi {
  @Autowired
  private UserService userService;

  @ApiOperation(value = "修改密码")
  @PatchMapping("change-pwd")
  @RequiresAuthentication
  public void changePwd(@RequestBody @Valid PwdChangeDto dto) {
    userService.changePassword(dto.getOldPwd(), dto.getNewPwd());
  }

  @ApiOperation(value = "获取职务列表")
  @GetMapping("actors")
  public List<ActorVo> listActors() {
    return ActorVo.forList(userService.getLogonUser());
  }

  @ApiOperation(value = "切换职务", responseContainer = "List")
  @ApiImplicitParam(name = "actorId", value = "职务ID", required = true)
  @PatchMapping("actor-switch")
  @RequiresAuthentication
  public List<String> changeActor(@RequestParam(required = true) @NotNull Long actorId) {
    userService.changeActor(actorId);
    return userService.getLogonUser().getDefaultActor().getRole().getPrivilegs();
  }
}
