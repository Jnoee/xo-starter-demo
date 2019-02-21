package com.github.jnoee.xo.starter.demo.vo;

import java.util.ArrayList;
import java.util.List;

import com.github.jnoee.xo.starter.demo.entity.Actor;
import com.github.jnoee.xo.starter.demo.entity.User;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ActorVo {
  @ApiModelProperty(value = "ID")
  private Long id;
  @ApiModelProperty(value = "名称")
  private String name;
  @ApiModelProperty(value = "是否默认职务")
  private Boolean defaulted;
  @ApiModelProperty(value = "机构ID")
  private Long organId;
  @ApiModelProperty(value = "机构名称")
  private String organName;
  @ApiModelProperty(value = "角色ID")
  private Long roleId;
  @ApiModelProperty(value = "角色名称")
  private String roleName;

  public static List<ActorVo> forList(User user) {
    List<ActorVo> vos = new ArrayList<>();
    for (Actor actor : user.getActors()) {
      ActorVo vo = forView(actor);
      vo.defaulted = user.getDefaultActor().equals(actor);
      vos.add(vo);
    }
    return vos;
  }

  public static ActorVo forEdit(Actor actor) {
    ActorVo vo = new ActorVo();
    vo.id = actor.getId();
    vo.name = actor.getName();
    vo.organId = actor.getOrgan().getId();
    vo.roleId = actor.getRole().getId();
    return vo;
  }

  private static ActorVo forView(Actor actor) {
    ActorVo vo = new ActorVo();
    vo.id = actor.getId();
    vo.name = actor.getName();
    vo.organName = actor.getOrgan().getName();
    vo.roleName = actor.getRole().getName();
    return vo;
  }
}
