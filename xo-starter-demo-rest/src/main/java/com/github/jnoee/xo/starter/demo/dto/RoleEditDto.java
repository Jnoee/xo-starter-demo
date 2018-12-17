package com.github.jnoee.xo.starter.demo.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RoleEditDto extends RoleAddDto {
  @NotBlank
  @ApiModelProperty(value = "ID", required = true)
  private String id;
}
