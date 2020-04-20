package com.github.jnoee.xo.starter.demo.api.organ;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class OrganEditReq extends OrganAddReq {
  @NotNull
  @ApiModelProperty(value = "ID", required = true)
  private Long id;
}
