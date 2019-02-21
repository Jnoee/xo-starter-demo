package com.github.jnoee.xo.starter.demo.api;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.jnoee.xo.model.Page;
import com.github.jnoee.xo.model.PageQuery;
import com.github.jnoee.xo.starter.demo.dto.UserAddDto;
import com.github.jnoee.xo.starter.demo.dto.UserEditDto;
import com.github.jnoee.xo.starter.demo.entity.User;
import com.github.jnoee.xo.starter.demo.service.UserService;
import com.github.jnoee.xo.starter.demo.vo.UserVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = "用户")
@RequiresPermissions("user:manage")
public class UserApi {
  @Autowired
  private UserService userService;

  @ApiOperation(value = "查询用户列表")
  @GetMapping
  public Page<UserVo> list(@Valid PageQuery query) {
    return UserVo.forPage(userService.search(query));
  }

  @ApiOperation(value = "获取用户信息")
  @GetMapping("{id}")
  public UserVo get(@PathVariable Long id) {
    return UserVo.forView(userService.get(id));
  }

  @ApiOperation(value = "新增用户")
  @PostMapping
  public void create(@RequestBody @Valid UserAddDto dto) {
    userService.create(dto.toUser());
  }

  @ApiOperation(value = "更新用户")
  @PutMapping
  public void update(@RequestBody @Valid UserEditDto dto) {
    userService.update(dto.toUser());
  }

  @ApiOperation(value = "停用用户")
  @PatchMapping("disable/{id}")
  public void disable(@PathVariable(value = "id") User user) {
    userService.disable(user);
  }

  @ApiOperation(value = "启用用户")
  @PatchMapping("enable/{id}")
  public void enable(@PathVariable(value = "id") User user) {
    userService.enable(user);
  }

  @ApiOperation(value = "重置密码")
  @PatchMapping("reset/{id}")
  public void reset(@PathVariable(value = "id") User user,
      @RequestParam(required = true) String managePassword) {
    userService.resetPassword(managePassword, user);
  }
}
