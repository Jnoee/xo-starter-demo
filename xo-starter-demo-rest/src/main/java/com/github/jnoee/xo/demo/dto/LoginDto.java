package com.github.jnoee.xo.demo.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginDto {
  @NotBlank(message = "名称不能为空")
  @ApiModelProperty(value = "名称", required = true)
  private String username;

  @NotBlank(message = "密码不能为空")
  @ApiModelProperty(value = "密码", required = true)
  private String password;
}
