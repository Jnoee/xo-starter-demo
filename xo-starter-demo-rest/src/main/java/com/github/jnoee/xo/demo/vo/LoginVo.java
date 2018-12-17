package com.github.jnoee.xo.demo.vo;

import java.util.ArrayList;
import java.util.List;

import com.github.jnoee.xo.demo.entity.User;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginVo {
  @ApiModelProperty(value = "ID")
  private String id;
  @ApiModelProperty(value = "姓名")
  private String name;
  @ApiModelProperty(value = "权限列表")
  private List<String> privilegs = new ArrayList<>();

  public LoginVo(User user, List<String> privilegs) {
    id = user.getId();
    name = user.getName();
    this.privilegs = privilegs;
  }
}
