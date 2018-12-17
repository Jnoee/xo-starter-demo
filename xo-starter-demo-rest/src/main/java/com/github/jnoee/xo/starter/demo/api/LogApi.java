package com.github.jnoee.xo.starter.demo.api;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.jnoee.xo.jpa.audit.service.BizLogService;
import com.github.jnoee.xo.model.Page;
import com.github.jnoee.xo.model.PageQuery;
import com.github.jnoee.xo.starter.demo.vo.LogVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "/logs", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = "日志")
public class LogApi {
  @Resource
  private BizLogService bizLogService;

  @ApiOperation(value = "查询日志列表")
  @GetMapping
  @RequiresPermissions("log:view")
  public Page<LogVo> list(@Valid PageQuery query) {
    return LogVo.forPage(bizLogService.searchLog(query));
  }

  @ApiOperation(value = "获取详细日志")
  @GetMapping("{id}")
  @RequiresPermissions("log:view")
  public LogVo get(@PathVariable String id) {
    return LogVo.forView(bizLogService.getLog(id));
  }
}
