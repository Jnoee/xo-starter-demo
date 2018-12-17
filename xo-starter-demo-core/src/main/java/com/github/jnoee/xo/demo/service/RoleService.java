package com.github.jnoee.xo.demo.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.jnoee.xo.demo.entity.Role;
import com.github.jnoee.xo.jpa.audit.annotation.DetailLog;
import com.github.jnoee.xo.jpa.audit.annotation.DetailLog.LogType;
import com.github.jnoee.xo.jpa.audit.annotation.SimpleLog;
import com.github.jnoee.xo.jpa.dao.Dao;
import com.github.jnoee.xo.message.MessageSource;
import com.github.jnoee.xo.utils.BeanUtils;

/**
 * 角色管理。
 */
@Service
public class RoleService {
  @Resource
  private Dao<Role> roleDao;
  @Resource
  private MessageSource messageSource;

  @Transactional(readOnly = true)
  public Role get(String roleId) {
    return roleDao.get(roleId);
  }

  @Transactional(readOnly = true)
  public List<Role> getAll() {
    return roleDao.getAll("createTime", true);
  }

  @Transactional
  @SimpleLog(code = "l.role.add", vars = "role.name")
  public void create(Role role) {
    if (!roleDao.isUnique(role, "name")) {
      messageSource.thrown("e.role.add.exist", role.getName());
    }
    roleDao.save(role);
  }

  @Transactional
  @DetailLog(target = "role", code = "l.role.edit", vars = "role.name", type = LogType.ALL)
  public void update(Role role) {
    if (!roleDao.isUnique(role, "name")) {
      messageSource.thrown("e.role.add.exist", role.getName());
    }
    Role origRole = get(role.getId());
    BeanUtils.copyFields(role, origRole);
  }
}
