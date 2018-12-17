package com.github.jnoee.xo.starter.demo.config;

import org.springframework.stereotype.Component;

import com.github.jnoee.xo.freemarker.AbstractFreeMarkerSettings;
import com.github.jnoee.xo.starter.demo.enums.EnabledStatus;

/**
 * FreeMarker配置组件。
 */
@Component("com.github.jnoee.xo.starter.demo.config.FreeMarkerSettings")
public class FreeMarkerSettings extends AbstractFreeMarkerSettings {
  /**
   * 构造方法。
   */
  public FreeMarkerSettings() {
    addTemplatePath("classpath:/com/github/jnoee/xo/starter/demo/action/");
    addTemplatePath("classpath:/com/github/jnoee/xo/starter/demo/macros/");
    addEnumClass(EnabledStatus.class);
    addAutoImport("sys", "sys.ftl");
  }
}
