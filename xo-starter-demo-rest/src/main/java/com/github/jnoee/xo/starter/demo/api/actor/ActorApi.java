package com.github.jnoee.xo.starter.demo.api.actor;

import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.jnoee.xo.starter.demo.entity.Actor;
import com.github.jnoee.xo.starter.demo.entity.User;
import com.github.jnoee.xo.starter.demo.service.ActorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;

@RestController
@RequestMapping(path = "/actor", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = "2.4.职务")
@RequiresRoles("user")
public class ActorApi {
  @Autowired
  private ActorService actorService;

  @ApiOperation(value = "2.4.1.查询列表")
  @GetMapping("list")
  @ApiOperationSupport(order = 1)
  public List<ActorRes> list(@RequestParam("userId") User user) {
    return ActorRes.forList(user);
  }

  @ApiOperation(value = "2.4.2.新增")
  @PostMapping("add")
  @ApiOperationSupport(order = 2)
  public void create(@RequestParam("userId") User user, @RequestBody @Valid ActorAddReq dto) {
    actorService.create(dto.toActor(user));
  }

  @ApiOperation(value = "2.4.3.更新")
  @PutMapping("update")
  @ApiOperationSupport(order = 3)
  public void update(@RequestParam("userId") User user, @RequestBody @Valid ActorEditReq dto) {
    actorService.update(dto.toActor(user));
  }

  @ApiOperation(value = "2.4.4.删除")
  @DeleteMapping("delete")
  @ApiOperationSupport(order = 4)
  public void delete(@RequestParam("id") Actor actor) {
    actorService.delete(actor);
  }

  @ApiOperation(value = "2.4.5.设置默认职务")
  @PatchMapping("set-default")
  @ApiOperationSupport(order = 5)
  public void setDefault(@RequestParam("id") Actor actor) {
    actorService.setDefault(actor);
  }
}
