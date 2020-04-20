package com.github.jnoee.xo.starter.demo.api;

import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.jnoee.xo.privileg.PrivilegManager;
import com.github.jnoee.xo.privileg.Privilegs;
import com.github.jnoee.xo.starter.demo.entity.User;
import com.github.jnoee.xo.starter.demo.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;

@RestController
@RequestMapping(path = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = "1.1.认证")
public class AuthApi {
  @Autowired
  private UserService userService;
  @Autowired
  private PrivilegManager privilegManager;

  @ApiOperation(value = "1.1.1.登录",
      notes = "登录成功后，Header中包含x-auth-token令牌。客户端保存token，并在后续访问的Header中包含该x-auth-token。")
  @PostMapping(path = "login")
  @ApiOperationSupport(order = 1)
  public LoginRes login(@RequestBody @Valid LoginReq dto) {
    userService.login(dto.getUsername(), dto.getPassword());
    User user = userService.getLogonUser();
    List<String> privilegs = userService.getAuthToken().getPrivilegs();
    return new LoginRes(user, privilegs);
  }

  @ApiOperation(value = "1.1.2.退出登录")
  @DeleteMapping("logout")
  @ApiOperationSupport(order = 2)
  @RequiresAuthentication
  public void logout() {
    userService.logout();
  }

  @ApiOperation(value = "1.1.3.获取系统所有权限列表")
  @GetMapping(path = "get-all-privilegs")
  @ApiOperationSupport(order = 3)
  @RequiresPermissions("role:manage")
  public Privilegs getPrivilegs() {
    return privilegManager.getPrivilegs();
  }
}
