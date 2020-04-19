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

import com.github.jnoee.xo.starter.demo.api.actor.ActorRes;
import com.github.jnoee.xo.starter.demo.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;

@RestController
@RequestMapping(path = "/person", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = "1.2.个人")
@RequiresAuthentication
public class PersonApi {
  @Autowired
  private UserService userService;

  @ApiOperation("1.2.1.修改密码")
  @PatchMapping("change-pwd")
  @ApiOperationSupport(order = 1)
  public void changePwd(@RequestBody @Valid PwdChangeReq req) {
    userService.changePassword(req.getOldPwd(), req.getNewPwd());
  }

  @ApiOperation("1.2.2.获取职务列表")
  @GetMapping("get-actors")
  @ApiOperationSupport(order = 2)
  public List<ActorRes> getActors() {
    return ActorRes.forList(userService.getLogonUser());
  }

  @ApiOperation(value = "1.2.3.切换职务", responseContainer = "List")
  @ApiImplicitParam(name = "actorId", value = "职务ID", required = true)
  @PatchMapping("switch-actor")
  @ApiOperationSupport(order = 3)
  public List<String> switchActor(@RequestParam(required = true) @NotNull Long actorId) {
    userService.changeActor(actorId);
    return userService.getLogonUser().getDefaultActor().getRole().getPrivilegs();
  }
}
