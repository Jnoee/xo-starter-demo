package com.github.jnoee.xo.starter.demo.api.entitycache;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.jnoee.xo.jpa.cache.EntityCacheManager;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;

@RestController
@RequestMapping(path = "/entity-cache", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = "2.7.缓存")
@RequiresPermissions("cache:manage")
public class EntityCacheApi {
  @Autowired
  private EntityCacheManager entityCacheManager;

  @ApiOperation(value = "2.7.1.查询列表", response = String.class, responseContainer = "List")
  @GetMapping("list")
  @ApiOperationSupport(order = 1)
  public List<Class<?>> listCachedEntity() {
    return entityCacheManager.getCachedEntityClasses();
  }

  @ApiOperation(value = "2.7.2.清理指定的实体类缓存")
  @ApiImplicitParam(name = "cachedEntityClasses", value = "缓存实体类名", required = true,
      dataType = "String", allowMultiple = true)
  @DeleteMapping("classes")
  @ApiOperationSupport(order = 2)
  public void evictCachedEntity(@RequestBody Class<?>[] cachedEntityClasses) {
    entityCacheManager.evictEntityRegion(cachedEntityClasses);
  }

  @ApiOperation(value = "2.7.3.清空所有实体缓存")
  @DeleteMapping("entity")
  @ApiOperationSupport(order = 3)
  public void evictEntityRegions() {
    entityCacheManager.evictEntityRegions();
  }

  @ApiOperation(value = "2.7.4.清空所有集合缓存")
  @DeleteMapping("collection")
  @ApiOperationSupport(order = 4)
  public void evictCollectionRegions() {
    entityCacheManager.evictCollectionRegions();
  }

  @ApiOperation(value = "2.7.5.清空所有查询缓存")
  @DeleteMapping("query")
  @ApiOperationSupport(order = 5)
  public void evictQueryRegions() {
    entityCacheManager.evictQueryRegions();
  }

  @ApiOperation(value = "2.7.6.清空所有缓存")
  @DeleteMapping("all")
  @ApiOperationSupport(order = 6)
  public void evictAllRegions() {
    entityCacheManager.evictAllRegions();
  }
}
