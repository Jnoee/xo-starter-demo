package com.github.jnoee.xo.starter.demo.api;

import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.jnoee.xo.starter.demo.dto.RoleAddDto;
import com.github.jnoee.xo.starter.demo.dto.RoleEditDto;
import com.github.jnoee.xo.starter.demo.entity.Role;
import com.github.jnoee.xo.starter.demo.service.RoleService;
import com.github.jnoee.xo.starter.demo.vo.RoleVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "/roles", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = "角色")
@RequiresPermissions("role:manage")
public class RoleApi {
  @Autowired
  private RoleService roleService;

  @ApiOperation(value = "查询角色列表")
  @GetMapping
  public List<RoleVo> list() {
    List<Role> roles = roleService.getAll();
    return RoleVo.forList(roles);
  }

  @ApiOperation(value = "获取角色信息")
  @GetMapping("{id}")
  public RoleVo get(@PathVariable Long id) {
    return RoleVo.forView(roleService.get(id));
  }

  @ApiOperation(value = "新增角色")
  @PostMapping
  public void save(@RequestBody @Valid RoleAddDto dto) {
    roleService.create(dto.toRole());
  }

  @ApiOperation(value = "更新角色")
  @PutMapping
  public void update(@RequestBody @Valid RoleEditDto dto) {
    roleService.update(dto.toRole());
  }
}
