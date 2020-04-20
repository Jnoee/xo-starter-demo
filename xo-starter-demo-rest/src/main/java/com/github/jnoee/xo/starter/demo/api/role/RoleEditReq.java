package com.github.jnoee.xo.starter.demo.api.role;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class RoleEditReq extends RoleAddReq {
  @NotNull
  @ApiModelProperty(value = "ID", required = true)
  private Long id;
}
