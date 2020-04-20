package com.github.jnoee.xo.starter.demo.api.role;

import java.util.ArrayList;
import java.util.List;

import com.github.jnoee.xo.starter.demo.entity.Role;
import com.github.jnoee.xo.utils.VoUtils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleRes {
  @ApiModelProperty(value = "ID")
  private Long id;
  @ApiModelProperty(value = "名称")
  private String name;
  @ApiModelProperty(value = "权限")
  private List<String> privilegs = new ArrayList<>();

  public static RoleRes forView(Role role) {
    return VoUtils.copy(role, RoleRes.class);
  }

  public static List<RoleRes> forList(List<Role> roles) {
    return VoUtils.copyExclude(roles, RoleRes.class, "privilegs");
  }
}
