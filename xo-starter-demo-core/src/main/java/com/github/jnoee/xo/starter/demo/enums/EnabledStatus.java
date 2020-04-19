package com.github.jnoee.xo.starter.demo.enums;


import com.github.jnoee.xo.constant.Color;
import com.github.jnoee.xo.ienum.IEnum;

import lombok.Getter;

/**
 * 启用状态。
 */
@Getter
public enum EnabledStatus implements IEnum {
  DISABLED("停用", "0", Color.GRAY), ENABLED("启用", "1", Color.GREEN);

  private String text;
  private String value;
  private String color;

  /**
   * 构造方法
   * 
   * @param text 文本
   * @param value 值
   * @param color 颜色
   */
  private EnabledStatus(String text, String value, String color) {
    this.text = text;
    this.value = value;
    this.color = color;
  }

  @Override
  public String toString() {
    return value;
  }
}
