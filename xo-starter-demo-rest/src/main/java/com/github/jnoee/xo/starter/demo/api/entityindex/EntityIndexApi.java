package com.github.jnoee.xo.starter.demo.api.entityindex;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.jnoee.xo.jpa.search.index.EntityIndexManager;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;

@RestController
@RequestMapping(path = "/entity-index", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = "2.6.索引")
@RequiresPermissions("index:manage")
public class EntityIndexApi {
  @Autowired
  private EntityIndexManager entityIndexManager;

  @ApiOperation(value = "2.6.1.查询列表", response = String.class, responseContainer = "List")
  @GetMapping("list")
  @ApiOperationSupport(order = 1)
  public List<Class<?>> listIndexedEntity() {
    return entityIndexManager.getIndexedEntityClasses();
  }

  @ApiOperation(value = "2.6.2.重建索引")
  @ApiImplicitParam(name = "indexedEntityClasses", value = "索引实体类名", required = true,
      dataType = "String", allowMultiple = true)
  @PostMapping("build")
  @ApiOperationSupport(order = 2)
  public void buildIndexedEntity(@RequestBody Class<?>[] indexedEntityClasses) {
    entityIndexManager.startAndWait(indexedEntityClasses);
  }
}
