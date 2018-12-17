package com.github.jnoee.xo.starter.demo.dto;

import javax.validation.constraints.NotBlank;

import com.github.jnoee.xo.starter.demo.entity.Actor;
import com.github.jnoee.xo.starter.demo.entity.Organ;
import com.github.jnoee.xo.starter.demo.entity.Role;
import com.github.jnoee.xo.starter.demo.entity.User;
import com.github.jnoee.xo.utils.VoUtils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ActorAddDto {
  @NotBlank
  @ApiModelProperty(value = "名称")
  private String name;
  @ApiModelProperty(value = "机构ID")
  private String organId;
  @ApiModelProperty(value = "角色ID")
  private String roleId;

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
