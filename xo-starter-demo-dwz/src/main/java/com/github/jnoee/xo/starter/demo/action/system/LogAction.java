package com.github.jnoee.xo.starter.demo.action.system;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.jnoee.xo.jpa.audit.entity.BizLog;
import com.github.jnoee.xo.jpa.audit.service.BizLogService;
import com.github.jnoee.xo.model.PageQuery;

/**
 * 日志管理。
 */
@Controller
@RequestMapping("/system")
@RequiresRoles("log")
public class LogAction {
  @Resource
  private BizLogService bizLogService;

  @RequestMapping("log-list")
  public void list(Model model, PageQuery query) {
    model.addAttribute("logPage", bizLogService.searchLog(query));
  }

  @RequestMapping("log-view")
  public void view(Model model, @RequestParam("log") BizLog log) {
    model.addAttribute("log", log);
  }

  /**
   * 查看指定业务实体ID的日志详细记录列表。
   * 
   * @param model 数据模型
   * @param entityId 业务实体ID
   */
  @RequestMapping("log-detail-list")
  public void listDetail(Model model, String entityId) {
    model.addAttribute("entityLogs", bizLogService.searchEntityLog(entityId));
  }
}
