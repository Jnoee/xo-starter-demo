package com.github.jnoee.xo.starter.demo.api.role;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RoleEditReq extends RoleAddReq {
  @NotNull
  @ApiModelProperty(value = "ID", required = true)
  private Long id;
}
