package com.github.jnoee.xo.starter.demo.api;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginReq {
  @NotBlank
  @ApiModelProperty(value = "用户名", required = true, example = "admin")
  private String username;
  @NotBlank
  @ApiModelProperty(value = "密码", required = true, example = "admin")
  private String password;
}
