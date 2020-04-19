package com.github.jnoee.xo.starter.demo.api;

import java.util.ArrayList;
import java.util.List;

import com.github.jnoee.xo.starter.demo.entity.User;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginRes {
  @ApiModelProperty(value = "ID")
  private Long id;
  @ApiModelProperty(value = "姓名")
  private String name;
  @ApiModelProperty(value = "机构名称")
  private String organName;
  @ApiModelProperty(value = "角色名称")
  private String roleName;
  @ApiModelProperty(value = "权限列表")
  private List<String> privilegs = new ArrayList<>();

  public LoginRes(User user, List<String> privilegs) {
    id = user.getId();
    name = user.getName();
    organName = user.getDefaultActor().getOrgan().getName();
    roleName = user.getDefaultActor().getRole().getName();
    this.privilegs = privilegs;
  }
}
