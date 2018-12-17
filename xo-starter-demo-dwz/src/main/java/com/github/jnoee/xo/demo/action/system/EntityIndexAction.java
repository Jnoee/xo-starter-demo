package com.github.jnoee.xo.demo.action.system;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.jnoee.xo.dwz.result.DwzResult;
import com.github.jnoee.xo.dwz.result.DwzResultBuild;
import com.github.jnoee.xo.jpa.search.index.EntityIndexManager;
import com.github.jnoee.xo.message.MessageSource;

@Controller
@RequestMapping("/system")
@RequiresPermissions("index:manage")
public class EntityIndexAction {
  @Resource
  private EntityIndexManager entityIndexManager;
  @Resource
  private MessageSource messageSource;

  /**
   * 查看全文索引实体类列表页面。
   * 
   * @param model 数据模型
   */
  @RequestMapping("indexed-entity-list")
  public void listIndexedEntity(Model model) {
    model.addAttribute("indexedEntityClasses", entityIndexManager.getIndexedEntityClasses());
  }

  /**
   * 重建实体类全文索引。
   * 
   * @param indexedEntityClasses 实体类列表
   * @return 返回操作成功信息。
   */
  @RequestMapping("indexed-entity-build")
  public @ResponseBody DwzResult buildIndexedEntity(Class<?>[] indexedEntityClasses) {
    entityIndexManager.startAndWait(indexedEntityClasses);
    return new DwzResultBuild().success("s.index.build").reloadNavTab().build();
  }
}
