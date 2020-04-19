package com.github.jnoee.xo.starter.demo.api.actor;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ActorEditReq extends ActorAddReq {
  @NotNull
  @ApiModelProperty(value = "ID", required = true)
  private Long id;
}
