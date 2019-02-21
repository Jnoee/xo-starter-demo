package com.github.jnoee.xo.starter.demo.api;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.jnoee.xo.constant.ValidGroup;
import com.github.jnoee.xo.starter.demo.dto.OrganAddDto;
import com.github.jnoee.xo.starter.demo.dto.OrganEditDto;
import com.github.jnoee.xo.starter.demo.entity.Organ;
import com.github.jnoee.xo.starter.demo.service.OrganService;
import com.github.jnoee.xo.starter.demo.service.UserService;
import com.github.jnoee.xo.starter.demo.vo.OrganVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "/organs", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = "机构")
@RequiresPermissions("organ:manage")
public class OrganApi {
  @Autowired
  private OrganService organService;
  @Autowired
  private UserService userService;

  @ApiOperation(value = "获取机构树信息")
  @GetMapping
  public OrganVo tree() {
    return OrganVo.forTree(userService.getCurrentOrgan());
  }

  @ApiOperation(value = "获取机构信息")
  @GetMapping("{id}")
  public OrganVo get(@PathVariable Long id) {
    return OrganVo.forView(organService.get(id));
  }

  @ApiOperation(value = "新增机构")
  @PostMapping
  public void create(@RequestBody @Validated(ValidGroup.Add.class) OrganAddDto dto) {
    organService.create(dto.toOrgan());
  }

  @ApiOperation(value = "更新机构")
  @PutMapping
  public void update(@RequestBody @Valid OrganEditDto dto) {
    organService.update(dto.toOrgan());
  }

  @ApiOperation(value = "启用机构")
  @PatchMapping("enable/{id}")
  public void enable(@PathVariable(value = "id") Organ organ) {
    organService.enable(organ);
  }

  @ApiOperation(value = "停用机构")
  @PatchMapping("disable/{id}")
  public void disable(@PathVariable(value = "id") Organ organ) {
    organService.disable(organ);
  }
}
