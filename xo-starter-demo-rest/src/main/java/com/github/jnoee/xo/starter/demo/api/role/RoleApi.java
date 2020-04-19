package com.github.jnoee.xo.starter.demo.api.role;

import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.jnoee.xo.starter.demo.entity.Role;
import com.github.jnoee.xo.starter.demo.service.RoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;

@RestController
@RequestMapping(path = "/role", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = "2.2.角色")
@RequiresPermissions("role:manage")
public class RoleApi {
  @Autowired
  private RoleService roleService;

  @ApiOperation(value = "2.2.1.查询列表")
  @GetMapping("list")
  @ApiOperationSupport(order = 1)
  public List<RoleRes> list() {
    List<Role> roles = roleService.getAll();
    return RoleRes.forList(roles);
  }

  @ApiOperation(value = "2.2.2.获取信息")
  @GetMapping("get")
  @ApiOperationSupport(order = 2)
  public RoleRes get(Long id) {
    return RoleRes.forView(roleService.get(id));
  }

  @ApiOperation(value = "2.2.3.新增")
  @PostMapping("add")
  @ApiOperationSupport(order = 3)
  public void save(@RequestBody @Valid RoleAddReq dto) {
    roleService.create(dto.toRole());
  }

  @ApiOperation(value = "2.2.4.更新")
  @PutMapping("update")
  @ApiOperationSupport(order = 4)
  public void update(@RequestBody @Valid RoleEditReq dto) {
    roleService.update(dto.toRole());
  }
}
