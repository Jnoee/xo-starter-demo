package com.github.jnoee.xo.starter.demo.action.system;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.jnoee.xo.dwz.result.DwzResult;
import com.github.jnoee.xo.dwz.result.DwzResultBuild;
import com.github.jnoee.xo.jpa.cache.EntityCacheManager;
import com.github.jnoee.xo.message.MessageSource;

@Controller
@RequestMapping("/system")
@RequiresPermissions("cache:manage")
public class EntityCacheAction {
  @Resource
  private EntityCacheManager entityCacheManager;
  @Resource
  private MessageSource messageSource;

  /**
   * 查看缓存实体类列表页面。
   * 
   * @param model 数据模型
   */
  @RequestMapping("cached-entity-list")
  public void listCachedEntity(Model model) {
    model.addAttribute("cachedEntityClasses", entityCacheManager.getCachedEntityClasses());
  }

  /**
   * 清理实体类缓存。
   * 
   * @param cachedEntityClasses 实体类列表
   * @return 返回操作成功信息。
   */
  @RequestMapping("cached-entity-evict")
  public @ResponseBody DwzResult evictCachedEntity(Class<?>[] cachedEntityClasses) {
    entityCacheManager.evictEntityRegion(cachedEntityClasses);
    return new DwzResultBuild().success("s.cache.evict-entity").reloadNavTab().build();
  }

  /**
   * 清空所有实体缓存。
   * 
   * @return 返回操作成功信息。
   */
  @RequestMapping("entity-regions-evict")
  public @ResponseBody DwzResult evictEntityRegions() {
    entityCacheManager.evictEntityRegions();
    return new DwzResultBuild().success("s.cache.evict-all-entity").reloadNavTab().build();
  }

  /**
   * 清空所有集合缓存。
   * 
   * @return 返回操作成功信息。
   */
  @RequestMapping("collection-regions-evict")
  public @ResponseBody DwzResult evictCollectionRegions() {
    entityCacheManager.evictCollectionRegions();
    return new DwzResultBuild().success("s.cache.evict-collection").reloadNavTab().build();
  }

  /**
   * 清空所有查询缓存。
   * 
   * @return 返回操作成功信息。
   */
  @RequestMapping("query-regions-evict")
  public @ResponseBody DwzResult evictQueryRegions() {
    entityCacheManager.evictQueryRegions();
    return new DwzResultBuild().success("s.cache.evict-query").reloadNavTab().build();
  }

  /**
   * 清空所有缓存。
   * 
   * @return 返回操作成功信息。
   */
  @RequestMapping("all-regions-evict")
  public @ResponseBody DwzResult evictAllRegions() {
    entityCacheManager.evictAllRegions();
    return new DwzResultBuild().success("s.cache.evict-all").reloadNavTab().build();
  }
}
