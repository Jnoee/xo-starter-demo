package com.github.jnoee.xo.starter.demo.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginDto {
  @NotBlank
  @ApiModelProperty(value = "用户名", required = true, example = "admin")
  private String username;
  @NotBlank
  @ApiModelProperty(value = "密码", required = true, example = "admin")
  private String password;
}
