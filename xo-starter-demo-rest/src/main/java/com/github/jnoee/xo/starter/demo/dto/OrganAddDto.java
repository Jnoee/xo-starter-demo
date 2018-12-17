package com.github.jnoee.xo.starter.demo.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Range;

import com.github.jnoee.xo.constant.ValidGroup;
import com.github.jnoee.xo.starter.demo.entity.Organ;
import com.github.jnoee.xo.utils.StringUtils;
import com.github.jnoee.xo.utils.VoUtils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrganAddDto {
  @NotBlank(groups = ValidGroup.Add.class)
  @ApiModelProperty(value = "父机构ID", required = true, allowEmptyValue = true)
  private String parentId;
  @NotBlank
  @ApiModelProperty(value = "名称", required = true)
  private String name;
  @Range(min = 1, max = 999)
  @ApiModelProperty(value = "序号", required = true, example = "999")
  private Integer ordinal;

  public Organ toOrgan() {
    Organ organ = VoUtils.copy(this, Organ.class);

    if (StringUtils.isNotBlank(parentId)) {
      Organ parentOrgan = new Organ();
      parentOrgan.setId(parentId);
      organ.setParent(parentOrgan);
    }

    return organ;
  }
}
