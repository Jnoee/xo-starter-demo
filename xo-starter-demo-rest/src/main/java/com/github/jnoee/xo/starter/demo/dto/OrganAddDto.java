package com.github.jnoee.xo.starter.demo.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.github.jnoee.xo.constant.ValidGroup;
import com.github.jnoee.xo.starter.demo.entity.Organ;
import com.github.jnoee.xo.utils.VoUtils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrganAddDto {
  @NotNull(groups = ValidGroup.Add.class)
  @ApiModelProperty(value = "父机构ID", required = true, allowEmptyValue = true)
  private Long parentId;
  @NotBlank
  @ApiModelProperty(value = "名称", required = true)
  private String name;
  @Range(min = 1, max = 999)
  @ApiModelProperty(value = "序号", required = true, example = "999")
  private Integer ordinal;

  public Organ toOrgan() {
    Organ organ = VoUtils.copy(this, Organ.class);

    if (parentId != null) {
      Organ parentOrgan = new Organ();
      parentOrgan.setId(parentId);
      organ.setParent(parentOrgan);
    }

    return organ;
  }
}
