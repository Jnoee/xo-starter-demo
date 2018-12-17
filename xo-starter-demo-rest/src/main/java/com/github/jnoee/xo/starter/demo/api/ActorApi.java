package com.github.jnoee.xo.starter.demo.api;

import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.jnoee.xo.starter.demo.dto.ActorAddDto;
import com.github.jnoee.xo.starter.demo.dto.ActorEditDto;
import com.github.jnoee.xo.starter.demo.entity.Actor;
import com.github.jnoee.xo.starter.demo.entity.User;
import com.github.jnoee.xo.starter.demo.service.ActorService;
import com.github.jnoee.xo.starter.demo.vo.ActorVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = "用户")
@RequiresRoles("user")
public class ActorApi {
  @Autowired
  private ActorService actorService;

  @ApiOperation(value = "查询职务列表")
  @GetMapping("{userId}/actors")
  public List<ActorVo> list(@PathVariable(value = "userId") User user) {
    return ActorVo.forList(user);
  }

  @ApiOperation(value = "新增职务")
  @PostMapping("{userId}/actors")
  public void create(@PathVariable(value = "userId") User user,
      @RequestBody @Valid ActorAddDto dto) {
    actorService.create(dto.toActor(user));
  }

  @ApiOperation(value = "更新职务")
  @PutMapping("{userId}/actors")
  public void update(@PathVariable(value = "userId") User user,
      @RequestBody @Valid ActorEditDto dto) {
    actorService.update(dto.toActor(user));
  }

  @ApiOperation(value = "删除职务")
  @DeleteMapping("{userId}/actors/{id}")
  public void delete(@PathVariable(value = "userId") User user,
      @PathVariable(value = "id") Actor actor) {
    actorService.delete(actor);
  }

  @ApiOperation(value = "设置默认职务")
  @PatchMapping("default-actor/{id}")
  public void setDefault(@PathVariable(value = "id") Actor actor) {
    actorService.setDefault(actor);
  }
}
