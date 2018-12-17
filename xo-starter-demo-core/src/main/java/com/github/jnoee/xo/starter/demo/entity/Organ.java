package com.github.jnoee.xo.starter.demo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import com.github.jnoee.xo.jpa.audit.annotation.LogField;
import com.github.jnoee.xo.jpa.audit.entity.AuditEntity;
import com.github.jnoee.xo.starter.demo.enums.EnabledStatus;

import lombok.Getter;
import lombok.Setter;

/**
 * 机构。
 */
@Entity
@Table(name = "Organ")
@Cache(region = "organ", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Getter
@Setter
public class Organ extends AuditEntity<User> {
  /** 上级机构 */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parentId")
  private Organ parent;
  /** 名称 */
  @LogField(text = "名称")
  private String name;
  /** 启用状态 */
  @Type(type = "IEnum")
  @LogField(text = "启用状态")
  private EnabledStatus status = EnabledStatus.ENABLED;
  /** 排序 */
  @LogField(text = "排序")
  private Integer ordinal = 999;
  /** 下级机构 */
  @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE,
      orphanRemoval = true)
  @OrderBy("ordinal,name")
  private List<Organ> childs = new ArrayList<>();
  /** 关联职务 */
  @OneToMany(mappedBy = "organ", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE,
      orphanRemoval = true)
  private List<Actor> actors = new ArrayList<>();

  /**
   * 获取机构完整名称(带上级机构名称)。
   * 
   * @return 返回机构完整名称。
   */
  public String getFullName() {
    if (parent != null) {
      return parent.getName() + "-" + name;
    } else {
      return name;
    }
  }

  /**
   * 获取用于下拉列表显示的机构名称。
   * 
   * @return 返回机构名称。
   */
  public String getSelectText() {
    StringBuilder builder = new StringBuilder(name);
    for (int i = 0; i < getOrganLevel(); i++) {
      builder.insert(0, "> ");
    }
    return builder.toString();
  }

  /**
   * 判断是否根机构。
   * 
   * @return 返回是否根机构。
   */
  public Boolean isRoot() {
    return parent == null;
  }

  /**
   * 获取机构所在层次，根机构层次为0。
   * 
   * @return 返回机构所在层次。
   */
  public Integer getOrganLevel() {
    if (isRoot()) {
      return 0;
    } else {
      return parent.getOrganLevel() + 1;
    }
  }

  /**
   * 获取本机构树状路线上的所有机构，包括本机构。
   * 
   * @return 返回本机构树状路线上的所有机构，包括本机构。
   */
  public List<Organ> getFullTree() {
    List<Organ> organTree = getParents();
    organTree.addAll(getChildTree());
    return organTree;
  }

  /**
   * 获取本机构树下的所有机构，包括本机构。
   * 
   * @return 返回本机构树下的所有机构，包括本机构。
   */
  public List<Organ> getChildTree() {
    List<Organ> organTree = new ArrayList<>();
    organTree.add(this);
    for (Organ child : getChilds()) {
      if (child.getStatus() == EnabledStatus.ENABLED) {
        organTree.addAll(child.getChildTree());
      }
    }
    return organTree;
  }

  /**
   * 获取本机构的所有上级机构，包括本机构。
   * 
   * @return 返回本机构的所有上级机构，包括本机构。
   */
  public List<Organ> getParentTree() {
    List<Organ> organTree = new ArrayList<>();
    organTree.add(this);
    if (getParent() != null) {
      organTree.addAll(getParents());
    }
    return organTree;
  }

  /**
   * 获取本机构的所有上级机构。
   * 
   * @return 返回本机构的所有上级机构。
   */
  public List<Organ> getParents() {
    List<Organ> parents = new ArrayList<>();
    if (getParent() != null) {
      parents.addAll(getParent().getParents());
      parents.add(getParent());
    }
    return parents;
  }
}
