package com.github.jnoee.xo.starter.demo.api.logs;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.jnoee.xo.jpa.audit.service.BizLogService;
import com.github.jnoee.xo.model.Page;
import com.github.jnoee.xo.model.PageQuery;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;

@RestController
@RequestMapping(path = "/log", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = "2.5.日志")
public class LogApi {
  @Resource
  private BizLogService bizLogService;

  @ApiOperation(value = "2.5.1.查询列表")
  @GetMapping("list")
  @RequiresPermissions("log:view")
  @ApiOperationSupport(order = 1)
  public Page<LogRes> list(@Valid PageQuery query) {
    return LogRes.forPage(bizLogService.searchLog(query));
  }

  @ApiOperation(value = "2.5.2.获取信息")
  @GetMapping("get")
  @RequiresPermissions("log:view")
  @ApiOperationSupport(order = 2)
  public LogRes get(Long id) {
    return LogRes.forView(bizLogService.getLog(id));
  }
}
