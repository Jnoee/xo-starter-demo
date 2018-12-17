package com.github.jnoee.xo.starter.demo.vo;

import java.util.Date;

import com.github.jnoee.xo.model.Page;
import com.github.jnoee.xo.starter.demo.entity.User;
import com.github.jnoee.xo.starter.demo.enums.EnabledStatus;
import com.github.jnoee.xo.utils.VoUtils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserVo {
  @ApiModelProperty(value = "ID")
  private String id;
  @ApiModelProperty(value = "用户名")
  private String username;
  @ApiModelProperty(value = "姓名")
  private String name;
  @ApiModelProperty(value = "默认职务")
  private String defaultActor;
  @ApiModelProperty(value = "创建时间")
  private Date createTime;
  @ApiModelProperty(value = "创建人")
  private String createUser;
  @ApiModelProperty(value = "修改时间")
  private Date updateTime;
  @ApiModelProperty(value = "修改人")
  private String updateUser;
  @ApiModelProperty(value = "启用状态")
  private EnabledStatus status;
  @ApiModelProperty(value = "序号")
  private Integer ordinal;

  public static UserVo forView(User user) {
    UserVo vo = VoUtils.copy(user, UserVo.class);
    vo.createUser = user.getCreateUser().getName();
    vo.updateUser = user.getUpdateUser().getName();
    vo.defaultActor = user.getDefaultActor().getFullName();
    return vo;
  }

  public static Page<UserVo> forPage(Page<User> userPage) {
    Page<UserVo> voPage = new Page<>(userPage.getCount(), userPage.getNumber(), userPage.getSize());
    for (User user : userPage.getContents()) {
      voPage.getContents().add(UserVo.forView(user));
    }
    return voPage;
  }
}
