package com.github.jnoee.xo.demo.api;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.jnoee.xo.demo.dto.UserDto;
import com.github.jnoee.xo.demo.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = "用户")
public class UserApi {
  @Autowired
  private UserService userService;

  @ApiOperation(value = "新增用户")
  @PostMapping
  @RequiresPermissions("user:manage")
  public void create(@Valid UserDto dto) {
    userService.create(dto.toUser());
  }
}
