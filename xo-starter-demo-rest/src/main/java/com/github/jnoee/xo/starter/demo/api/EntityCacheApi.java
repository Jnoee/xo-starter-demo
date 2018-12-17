package com.github.jnoee.xo.starter.demo.api;

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

@RestController
@RequestMapping(path = "/entity-caches", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = "缓存")
@RequiresPermissions("cache:manage")
public class EntityCacheApi {
  @Autowired
  private EntityCacheManager entityCacheManager;

  @ApiOperation(value = "查询缓存列表", response = String.class, responseContainer = "List")
  @GetMapping
  public List<Class<?>> listCachedEntity() {
    return entityCacheManager.getCachedEntityClasses();
  }

  @ApiOperation(value = "清理指定的实体类缓存")
  @ApiImplicitParam(name = "cachedEntityClasses", value = "缓存实体类名", required = true,
      dataType = "String", allowMultiple = true)
  @DeleteMapping("classes")
  public void evictCachedEntity(@RequestBody Class<?>[] cachedEntityClasses) {
    entityCacheManager.evictEntityRegion(cachedEntityClasses);
  }

  @ApiOperation(value = "清空所有实体缓存")
  @DeleteMapping("entity")
  public void evictEntityRegions() {
    entityCacheManager.evictEntityRegions();
  }

  @ApiOperation(value = "清空所有集合缓存")
  @DeleteMapping("collection")
  public void evictCollectionRegions() {
    entityCacheManager.evictCollectionRegions();
  }

  @ApiOperation(value = "清空所有查询缓存")
  @DeleteMapping("query")
  public void evictQueryRegions() {
    entityCacheManager.evictQueryRegions();
  }

  @ApiOperation(value = "清空所有缓存")
  @DeleteMapping("all")
  public void evictAllRegions() {
    entityCacheManager.evictAllRegions();
  }
}
