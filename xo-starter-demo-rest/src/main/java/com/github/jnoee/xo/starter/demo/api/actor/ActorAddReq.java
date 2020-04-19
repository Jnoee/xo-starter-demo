package com.github.jnoee.xo.starter.demo.api.actor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.github.jnoee.xo.starter.demo.entity.Actor;
import com.github.jnoee.xo.starter.demo.entity.Organ;
import com.github.jnoee.xo.starter.demo.entity.Role;
import com.github.jnoee.xo.starter.demo.entity.User;
import com.github.jnoee.xo.utils.VoUtils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ActorAddReq {
  @NotBlank
  @ApiModelProperty(value = "名称")
  private String name;
  @NotNull
  @ApiModelProperty(value = "机构ID")
  private Long organId;
  @NotNull
  @ApiModelProperty(value = "角色ID")
  private Long roleId;

  public Actor toActor(User user) {
    Actor actor = VoUtils.copy(this, Actor.class);
    actor.setUser(user);

    Organ organ = new Organ();
    organ.setId(organId);
    actor.setOrgan(organ);

    Role role = new Role();
    role.setId(roleId);
    actor.setRole(role);

    return actor;
  }
}
