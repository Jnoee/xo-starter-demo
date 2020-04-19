package com.github.jnoee.xo.starter.demo.api.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.github.jnoee.xo.starter.demo.entity.Actor;
import com.github.jnoee.xo.starter.demo.entity.Organ;
import com.github.jnoee.xo.starter.demo.entity.Role;
import com.github.jnoee.xo.starter.demo.entity.User;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserAddReq {
  @NotBlank
  @ApiModelProperty(value = "用户名", required = true, example = "test001")
  private String username;
  @NotBlank
  @ApiModelProperty(value = "姓名", required = true, example = "测试用户")
  private String name;
  @NotNull
  @Range(min = 1, max = 999)
  @ApiModelProperty(value = "序号", required = true, example = "999")
  private Integer ordinal;
  @NotNull
  @ApiModelProperty(value = "机构ID", required = true, example = "100000000000000000")
  private Long organId;
  @NotNull
  @ApiModelProperty(value = "角色ID", required = true, example = "100000000000000002")
  private Long roleId;
  @NotBlank
  @ApiModelProperty(value = "职务名称", required = true, example = "测试组长")
  private String actorName;

  public User toUser() {
    User user = new User();
    user.setUsername(username);
    user.setName(name);
    user.setOrdinal(ordinal);

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
