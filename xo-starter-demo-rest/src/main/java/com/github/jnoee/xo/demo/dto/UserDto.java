package com.github.jnoee.xo.demo.dto;

import javax.validation.constraints.NotBlank;

import com.github.jnoee.xo.demo.entity.Actor;
import com.github.jnoee.xo.demo.entity.Organ;
import com.github.jnoee.xo.demo.entity.Role;
import com.github.jnoee.xo.demo.entity.User;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserDto {
  @NotBlank(message = "用户名不能为空")
  @ApiModelProperty(value = "用户名", required = true)
  private String username;
  @NotBlank(message = "姓名不能为空")
  @ApiModelProperty(value = "姓名", required = true)
  private String name;
  @NotBlank(message = "机构ID不能为空")
  @ApiModelProperty(value = "机构ID", required = true)
  private String organId;
  @NotBlank(message = "角色ID不能为空")
  @ApiModelProperty(value = "角色ID", required = true)
  private String roleId;
  @NotBlank(message = "职务名称不能为空")
  @ApiModelProperty(value = "职务名称", required = true)
  private String actorName;

  public User toUser() {
    User user = new User();
    user.setUsername(username);
    user.setName(name);

    Organ organ = new Organ();
    organ.setId(organId);
    Role role = new Role();
    role.setId(roleId);

    Actor actor = new Actor();
    actor.setName(actorName);
    actor.setOrgan(organ);
    actor.setRole(role);
    user.setDefaultActor(actor);
    return user;
  }
}
