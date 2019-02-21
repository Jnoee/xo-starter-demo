package com.github.jnoee.xo.starter.demo.vo;

import java.util.ArrayList;
import java.util.List;

import com.github.jnoee.xo.starter.demo.entity.Role;
import com.github.jnoee.xo.utils.VoUtils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoleVo {
  @ApiModelProperty(value = "ID")
  private Long id;
  @ApiModelProperty(value = "名称")
  private String name;
  @ApiModelProperty(value = "权限")
  private List<String> privilegs = new ArrayList<>();

  public static RoleVo forView(Role role) {
    return VoUtils.copy(role, RoleVo.class);
  }

  public static List<RoleVo> forList(List<Role> roles) {
    return VoUtils.copy(roles, RoleVo.class, "privilegs");
  }
}
