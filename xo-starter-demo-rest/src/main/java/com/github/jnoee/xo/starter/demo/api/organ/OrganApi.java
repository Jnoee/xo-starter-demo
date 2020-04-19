package com.github.jnoee.xo.starter.demo.api.organ;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.jnoee.xo.constant.ValidGroup;
import com.github.jnoee.xo.starter.demo.entity.Organ;
import com.github.jnoee.xo.starter.demo.service.OrganService;
import com.github.jnoee.xo.starter.demo.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;

@RestController
@RequestMapping(path = "/organ", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = "2.1.机构")
@RequiresPermissions("organ:manage")
public class OrganApi {
  @Autowired
  private OrganService organService;
  @Autowired
  private UserService userService;

  @ApiOperation(value = "2.1.1.获取树")
  @GetMapping("tree")
  @ApiOperationSupport(order = 1)
  public OrganRes tree() {
    return OrganRes.forTree(userService.getCurrentOrgan());
  }

  @ApiOperation(value = "2.1.2.获取信息")
  @GetMapping("get")
  @ApiOperationSupport(order = 2)
  public OrganRes get(Long id) {
    return OrganRes.forView(organService.get(id));
  }

  @ApiOperation(value = "2.1.3.新增")
  @PostMapping("add")
  @ApiOperationSupport(order = 3)
  public void create(@RequestBody @Validated(ValidGroup.Add.class) OrganAddReq dto) {
    organService.create(dto.toOrgan());
  }

  @ApiOperation(value = "2.1.4.更新")
  @PutMapping("update")
  @ApiOperationSupport(order = 4)
  public void update(@RequestBody @Valid OrganEditReq dto) {
    organService.update(dto.toOrgan());
  }

  @ApiOperation(value = "2.1.5.启用")
  @PatchMapping("enable")
  @ApiOperationSupport(order = 5)
  public void enable(@RequestParam("id") Organ organ) {
    organService.enable(organ);
  }

  @ApiOperation(value = "2.1.6.停用")
  @PatchMapping("disable")
  @ApiOperationSupport(order = 6)
  public void disable(@RequestParam("id") Organ organ) {
    organService.disable(organ);
  }
}
