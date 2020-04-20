package com.github.jnoee.xo.starter.demo.api;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PwdChangeReq {
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
