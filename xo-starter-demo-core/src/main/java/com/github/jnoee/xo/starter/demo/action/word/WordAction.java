package com.github.jnoee.xo.starter.demo.action.word;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.jnoee.xo.starter.demo.action.word.model.Software;
import com.github.jnoee.xo.word.Word;
import com.github.jnoee.xo.word.WordFactory;
import com.github.jnoee.xo.word.WordView;

@Controller
@RequestMapping("/word")
public class WordAction {
  @Autowired
  private WordFactory wordFactory;

  /**
   * 导出软件说明书文档。
   * 
   * @return 返回软件说明书文档。
   */
  @RequestMapping("software")
  public WordView exportSoftware() {
    Word word = wordFactory.create("software", new Software());
    return new WordView("软件说明书", word);
  }
}
