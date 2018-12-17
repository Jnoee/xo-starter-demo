package com.github.jnoee.xo.starter.demo.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PwdChangeDto {
  @NotBlank
  @ApiModelProperty(value = "旧密码", required = true, example = "666666")
  private String oldPwd;
  @NotBlank
  @ApiModelProperty(value = "新密码", required = true, example = "666666")
  private String newPwd;
  @NotBlank
  @ApiModelProperty(value = "确认密码", required = true, example = "666666")
  private String confirmPwd;
}
