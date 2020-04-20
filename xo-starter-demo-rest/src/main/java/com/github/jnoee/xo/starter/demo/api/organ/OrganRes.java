package com.github.jnoee.xo.starter.demo.api.organ;

import java.util.ArrayList;
import java.util.List;

import com.github.jnoee.xo.starter.demo.entity.Organ;
import com.github.jnoee.xo.starter.demo.enums.EnabledStatus;
import com.github.jnoee.xo.utils.VoUtils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganRes {
  @ApiModelProperty(value = "ID")
  private Long id;
  @ApiModelProperty(value = "名称")
  private String name;
  @ApiModelProperty(value = "启用状态")
  private EnabledStatus status;
  @ApiModelProperty(value = "排序")
  private Integer ordinal;
  @ApiModelProperty(value = "下级机构")
  private List<OrganRes> childs = new ArrayList<>();

  public static OrganRes forView(Organ organ) {
    return VoUtils.copyExclude(organ, OrganRes.class, "childs");
  }

  public static OrganRes forTree(Organ organ) {
    OrganRes vo = forView(organ);
    for (Organ child : organ.getChilds()) {
      vo.childs.add(forTree(child));
    }
    return vo;
  }
}
