package com.github.jnoee.xo.starter.demo.action.excel;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.jnoee.xo.excel.Excel;
import com.github.jnoee.xo.excel.ExcelFactory;
import com.github.jnoee.xo.excel.ExcelView;
import com.github.jnoee.xo.starter.demo.action.excel.model.Department;

@Controller
@RequestMapping("/excel")
public class ExcelAction {
  @Autowired
  private ExcelFactory excelFactory;

  /**
   * 导出基础样式表格。
   * 
   * @return 返回基础样式表格。
   */
  @RequestMapping("basic")
  public ExcelView exportSoftware() {
    Map<String, Object> model = new HashMap<>();
    model.put("department", Department.single());
    Excel excel = excelFactory.create("basic", model);
    return new ExcelView("基础样式表格", excel);
  }
}
