package com.github.jnoee.xo.starter.demo.api.logs;

import java.util.Date;

import com.github.jnoee.xo.jpa.audit.entity.BizLog;
import com.github.jnoee.xo.model.Page;
import com.github.jnoee.xo.utils.VoUtils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogRes {
  @ApiModelProperty(value = "ID")
  private Long id;
  @ApiModelProperty(value = "操作人")
  private String operator;
  @ApiModelProperty(value = "操作时间")
  private Date operateTime;
  @ApiModelProperty(value = "日志信息")
  private String message;
  @ApiModelProperty(value = "原数据")
  private String origData;
  @ApiModelProperty(value = "新数据")
  private String newData;
  @ApiModelProperty(value = "是否记录了业务数据")
  private Boolean hasData;

  public static LogRes forView(BizLog bizLog) {
    LogRes vo = VoUtils.copy(bizLog, LogRes.class);
    vo.hasData = bizLog.hasData();
    return vo;
  }

  public static Page<LogRes> forPage(Page<BizLog> bizLogPage) {
    Page<LogRes> voPage =
        new Page<>(bizLogPage.getCount(), bizLogPage.getNumber(), bizLogPage.getSize());
    for (BizLog bizLog : bizLogPage.getContents()) {
      voPage.getContents().add(LogRes.forList(bizLog));
    }
    return voPage;
  }

  private static LogRes forList(BizLog bizLog) {
    LogRes vo = VoUtils.copyExclude(bizLog, LogRes.class, "newData,origData");
    vo.hasData = bizLog.hasData();
    return vo;
  }
}
