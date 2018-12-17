package com.github.jnoee.xo.starter.demo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.github.jnoee.xo.jpa.audit.annotation.LogField;
import com.github.jnoee.xo.jpa.audit.entity.AuditEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 角色。
 */
@Entity
@Table(name = "Role")
@Cache(region = "role", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Getter
@Setter
public class Role extends AuditEntity<User> {
  /** 名称 */
  @LogField(text = "名称")
  private String name;
  /** 权限 */
  @LogField(text = "权限")
  private List<String> privilegs = new ArrayList<>();
  /** 关联职务 */
  @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE,
      orphanRemoval = true)
  private List<Actor> actors = new ArrayList<>();
}
