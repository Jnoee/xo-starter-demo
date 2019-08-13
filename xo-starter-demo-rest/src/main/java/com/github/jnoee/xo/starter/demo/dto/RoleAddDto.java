package com.github.jnoee.xo.starter.demo.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.github.jnoee.xo.starter.demo.entity.Role;
import com.github.jnoee.xo.utils.CollectionUtils;
import com.github.jnoee.xo.utils.VoUtils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoleAddDto {
  @NotBlank
  @ApiModelProperty(value = "名称", required = true)
  private String name;
  @NotNull
  @Size(min = 1)
  @ApiModelProperty(value = "权限", required = true)
  private String[] privilegs;

  public Role toRole() {
    Role role = VoUtils.copyExclude(this, Role.class, "privilegs");
    role.setPrivilegs(CollectionUtils.toList(privilegs));
    return role;
  }
}
