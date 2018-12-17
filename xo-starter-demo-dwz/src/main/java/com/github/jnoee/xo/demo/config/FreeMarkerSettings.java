package com.github.jnoee.xo.demo.config;

import org.springframework.stereotype.Component;

import com.github.jnoee.xo.demo.enums.EnabledStatus;
import com.github.jnoee.xo.freemarker.AbstractFreeMarkerSettings;

/**
 * FreeMarker配置组件。
 */
@Component("com.github.jnoee.xo.demo.config.FreeMarkerSettings")
public class FreeMarkerSettings extends AbstractFreeMarkerSettings {
  /**
   * 构造方法。
   */
  public FreeMarkerSettings() {
    addTemplatePath("classpath:/com/github/jnoee/xo/demo/action/");
    addTemplatePath("classpath:/com/github/jnoee/xo/demo/macros/");
    addEnumClass(EnabledStatus.class);
    addAutoImport("sys", "sys.ftl");
  }
}
