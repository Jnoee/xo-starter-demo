package com.github.jnoee.xo.starter.demo.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.github.jnoee.xo.jpa.entity.UuidEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 职务。
 */
@Entity
@Table(name = "Actor")
@Cache(region = "actor", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Getter
@Setter
public class Actor extends UuidEntity {
  /** 关联机构 */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "organId")
  private Organ organ;
  /** 关联用户 */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userId")
  private User user;
  /** 关联角色 */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "roleId")
  private Role role;
  /** 名称 */
  private String name;

  /**
   * 获取职务的完整名称。（机构名-职务名）
   * 
   * @return 返回职务的完整名称。
   */
  public String getFullName() {
    return organ.getName() + "-" + name;
  }

  /**
   * 是否为关联用户的默认职务。
   * 
   * @return 返回是否为关联用户的默认职务。
   */
  public Boolean isDefaultActor() {
    return user.getDefaultActor().getId().equals(id);
  }
}
