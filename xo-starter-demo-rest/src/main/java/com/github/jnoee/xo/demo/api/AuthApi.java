package com.github.jnoee.xo.demo.api;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.jnoee.xo.demo.dto.LoginDto;
import com.github.jnoee.xo.demo.entity.User;
import com.github.jnoee.xo.demo.service.UserService;
import com.github.jnoee.xo.demo.vo.LoginVo;
import com.github.jnoee.xo.privileg.PrivilegManager;
import com.github.jnoee.xo.privileg.Privilegs;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = "认证")
public class AuthApi {
  @Autowired
  private UserService userService;
  @Autowired
  private PrivilegManager privilegManager;

  @ApiOperation(value = "登录",
      notes = "登录成功后，Header中包含x-auth-token令牌。客户端保存token，并在后续访问的Header中包含该x-auth-token。")
  @PostMapping(path = "login")
  public LoginVo login(LoginDto dto) {
    userService.login(dto.getUsername(), dto.getPassword());
    User user = userService.getLogonUser();
    List<String> privilegs = userService.getAuthToken().getPrivilegs();
    return new LoginVo(user, privilegs);
  }

  @ApiOperation(value = "获取系统所有权限列表")
  @GetMapping(path = "privilegs")
  @RequiresPermissions("role:manage")
  public Privilegs getPrivilegs() {
    return privilegManager.getPrivilegs();
  }
}
