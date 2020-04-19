package com.github.jnoee.xo.starter.demo.api.user;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.jnoee.xo.model.Page;
import com.github.jnoee.xo.model.PageQuery;
import com.github.jnoee.xo.starter.demo.entity.User;
import com.github.jnoee.xo.starter.demo.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;

@RestController
@RequestMapping(path = "/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = "2.3.用户")
@RequiresPermissions("user:manage")
public class UserApi {
  @Autowired
  private UserService userService;

  @ApiOperation(value = "2.3.1.查询列表")
  @GetMapping("list")
  @ApiOperationSupport(order = 1)
  public Page<UserRes> list(@Valid PageQuery query) {
    return UserRes.forPage(userService.search(query));
  }

  @ApiOperation(value = "2.3.2.获取信息")
  @GetMapping("get")
  @ApiOperationSupport(order = 2)
  public UserRes get(Long id) {
    return UserRes.forView(userService.get(id));
  }

  @ApiOperation(value = "2.3.3.新增")
  @PostMapping("add")
  @ApiOperationSupport(order = 3)
  public void create(@RequestBody @Valid UserAddReq dto) {
    userService.create(dto.toUser());
  }

  @ApiOperation(value = "2.3.4.更新")
  @PutMapping("update")
  @ApiOperationSupport(order = 4)
  public void update(@RequestBody @Valid UserEditReq dto) {
    userService.update(dto.toUser());
  }

  @ApiOperation(value = "2.3.5.停用")
  @PatchMapping("disable")
  @ApiOperationSupport(order = 5)
  public void disable(@RequestParam("id") User user) {
    userService.disable(user);
  }

  @ApiOperation(value = "2.3.6.启用")
  @PatchMapping("enable")
  @ApiOperationSupport(order = 6)
  public void enable(@RequestParam("id") User user) {
    userService.enable(user);
  }

  @ApiOperation(value = "2.3.7.重置密码")
  @PatchMapping("reset")
  @ApiOperationSupport(order = 7)
  public void reset(@RequestParam("id") User user, String managePassword) {
    userService.resetPassword(managePassword, user);
  }
}
