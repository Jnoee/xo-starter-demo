package com.github.jnoee.xo.starter.demo.vo;

import java.util.ArrayList;
import java.util.List;

import com.github.jnoee.xo.starter.demo.entity.Organ;
import com.github.jnoee.xo.starter.demo.enums.EnabledStatus;
import com.github.jnoee.xo.utils.VoUtils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrganVo {
  @ApiModelProperty(value = "ID")
  private Long id;
  @ApiModelProperty(value = "名称")
  private String name;
  @ApiModelProperty(value = "启用状态")
  private EnabledStatus status;
  @ApiModelProperty(value = "排序")
  private Integer ordinal;
  @ApiModelProperty(value = "下级机构")
  private List<OrganVo> childs = new ArrayList<>();

  public static OrganVo forView(Organ organ) {
    return VoUtils.copy(organ, OrganVo.class, "childs");
  }

  public static OrganVo forTree(Organ organ) {
    OrganVo vo = forView(organ);
    for (Organ child : organ.getChilds()) {
      vo.childs.add(forTree(child));
    }
    return vo;
  }
}
