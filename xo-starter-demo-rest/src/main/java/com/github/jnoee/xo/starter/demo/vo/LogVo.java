package com.github.jnoee.xo.starter.demo.vo;

import java.util.Date;

import com.github.jnoee.xo.jpa.audit.entity.BizLog;
import com.github.jnoee.xo.model.Page;
import com.github.jnoee.xo.utils.VoUtils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LogVo {
  @ApiModelProperty(value = "ID")
  private String id;
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

  public static LogVo forView(BizLog bizLog) {
    LogVo vo = VoUtils.copy(bizLog, LogVo.class);
    vo.hasData = bizLog.hasData();
    return vo;
  }

  public static Page<LogVo> forPage(Page<BizLog> bizLogPage) {
    Page<LogVo> voPage =
        new Page<>(bizLogPage.getCount(), bizLogPage.getNumber(), bizLogPage.getSize());
    for (BizLog bizLog : bizLogPage.getContents()) {
      voPage.getContents().add(LogVo.forList(bizLog));
    }
    return voPage;
  }

  private static LogVo forList(BizLog bizLog) {
    LogVo vo = VoUtils.copy(bizLog, LogVo.class, "newData,origData");
    vo.hasData = bizLog.hasData();
    return vo;
  }
}
