package com.github.jnoee.xo.demo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import com.github.jnoee.xo.auth.server.AuthUser;
import com.github.jnoee.xo.demo.enums.EnabledStatus;
import com.github.jnoee.xo.jpa.audit.annotation.LogField;
import com.github.jnoee.xo.jpa.audit.entity.AuditEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户。
 */
@Entity
@Table(name = "User")
@Cache(region = "user", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Getter
@Setter
public class User extends AuditEntity<User> implements AuthUser {
  /** 姓名 */
  @LogField(text = "姓名")
  private String name;
  /** 用户名 */
  @LogField(text = "用户名")
  private String username;
  /** 密码 */
  private String password;
  /** 启用状态 */
  @LogField(text = "启用状态")
  @Type(type = "IEnum")
  private EnabledStatus status = EnabledStatus.ENABLED;
  /** 排序 */
  @LogField(text = "排序")
  private Integer ordinal = 999;
  /** 默认职务 */
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "defaultActorId")
  private Actor defaultActor;
  /** 用户职务 */
  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE,
      orphanRemoval = true)
  @OrderBy("name")
  private List<Actor> actors = new ArrayList<>();

  /**
   * 判断是否超级管理员。
   * 
   * @return 返回是否超级管理员。
   */
  public Boolean isAdmin() {
    return "admin".equals(username);
  }

  @Override
  public Boolean getEnabled() {
    return status == EnabledStatus.ENABLED;
  }
}
