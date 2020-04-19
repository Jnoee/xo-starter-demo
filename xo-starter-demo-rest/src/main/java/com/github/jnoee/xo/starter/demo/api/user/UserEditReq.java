package com.github.jnoee.xo.starter.demo.api.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.github.jnoee.xo.starter.demo.entity.User;
import com.github.jnoee.xo.utils.VoUtils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserEditReq {
  @NotNull
  @ApiModelProperty(value = "用户ID", required = true)
  private Long id;
  @NotBlank
  @ApiModelProperty(value = "用户名", required = true)
  private String username;
  @NotBlank
  @ApiModelProperty(value = "姓名", required = true)
  private String name;
  @NotNull
  @Range(min = 1, max = 999)
  @ApiModelProperty(value = "序号", required = true, example = "999")
  private Integer ordinal;

  public User toUser() {
    return VoUtils.copy(this, User.class);
  }
}
