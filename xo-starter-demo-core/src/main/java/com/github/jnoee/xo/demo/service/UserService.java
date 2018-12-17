package com.github.jnoee.xo.demo.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.jnoee.xo.auth.AuthHelper;
import com.github.jnoee.xo.auth.AuthToken;
import com.github.jnoee.xo.auth.server.AuthUserService;
import com.github.jnoee.xo.demo.constants.AdminIds;
import com.github.jnoee.xo.demo.entity.Actor;
import com.github.jnoee.xo.demo.entity.Organ;
import com.github.jnoee.xo.demo.entity.User;
import com.github.jnoee.xo.demo.enums.EnabledStatus;
import com.github.jnoee.xo.jpa.audit.annotation.DetailLog;
import com.github.jnoee.xo.jpa.audit.annotation.DetailLog.LogType;
import com.github.jnoee.xo.jpa.audit.annotation.SimpleLog;
import com.github.jnoee.xo.jpa.dao.Dao;
import com.github.jnoee.xo.jpa.query.Criteria;
import com.github.jnoee.xo.message.MessageSource;
import com.github.jnoee.xo.model.Page;
import com.github.jnoee.xo.model.PageQuery;
import com.github.jnoee.xo.utils.BeanUtils;
import com.github.jnoee.xo.utils.StringUtils;

/**
 * 用户管理。
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService implements AuthUserService<User> {
  @Resource
  private Dao<User> userDao;
  @Resource
  private Dao<Actor> actorDao;
  @Autowired
  private AuthHelper authHelper;
  @Autowired
  private MessageSource messageSource;

  @Override
  public User getByUsername(String username) {
    return userDao.findUnique("username", username);
  }

  @Override
  public AuthToken genAuthToken(String username) {
    User user = getByUsername(username);
    AuthToken token = new AuthToken();
    token.setUsername(username);
    token.setPrivilegs(user.getDefaultActor().getRole().getPrivilegs());
    return token;
  }

  @Transactional(readOnly = true)
  public Page<User> search(PageQuery query) {
    Criteria<User> criteria = userDao.createCriteria();
    criteria.desc("createTime");
    // 将系统管理员从搜索的用户结果中排除
    criteria.notEq("id", AdminIds.USER_ID);
    return userDao.findPage(criteria, query);
  }

  @Transactional(readOnly = true)
  public User getUser(String id) {
    return userDao.get(id);
  }

  @Transactional
  @SimpleLog(code = "l.user.add", vars = "user.name")
  public void create(User user) {
    if (!userDao.isUnique(user, "username")) {
      messageSource.thrown("e.user.add.exist", user.getUsername());
    }
    if (StringUtils.isEmpty(user.getPassword())) {
      user.setPassword(authHelper.encodePassword(AdminIds.NEW_SALT));
    }
    userDao.save(user);

    Actor defaultActor = user.getDefaultActor();
    defaultActor.setUser(user);
    actorDao.save(defaultActor);
  }

  @Transactional
  @DetailLog(target = "user", code = "l.user.edit", vars = "user.name", type = LogType.ALL)
  public void update(User user) {
    if (!userDao.isUnique(user, "username")) {
      messageSource.thrown("e.user.add.exist", user.getUsername());
    }
    User origUser = getUser(user.getId());
    BeanUtils.copyFields(user, origUser, "enabled");
  }

  @Transactional
  @DetailLog(target = "user", code = "l.user.delete", vars = "user.name", type = LogType.ORIG)
  public void delete(User user) {
    userDao.remove(user);
  }

  @Transactional
  @SimpleLog(code = "l.user.enable", vars = "user.name")
  public void enable(User user) {
    user.setStatus(EnabledStatus.ENABLED);
  }

  @Transactional
  @SimpleLog(code = "l.user.disable", vars = "user.name")
  public void disable(User user) {
    user.setStatus(EnabledStatus.DISABLED);
  }

  /**
   * 重置密码。
   *
   * @param managePassword 管理员密码
   * @param user 重置用户
   */
  @Transactional
  @SimpleLog(code = "l.user.reset-pwd", vars = "user.name")
  public void resetPassword(String managePassword, User user) {
    if (!authHelper.verifyPassword(managePassword, getLogonUser().getPassword())) {
      messageSource.thrown("e.user.reset-pwd.admin-pwd-wrong");
    }
    user.setPassword(authHelper.encodePassword(AdminIds.NEW_SALT));
  }

  /**
   * 修改密码。
   *
   * @param oldPwd 原密码
   * @param newPwd 新密码
   */
  @Transactional
  @SimpleLog(code = "l.user.change-pwd")
  public void changePassword(String oldPwd, String newPwd) {
    User user = getLogonUser();
    if (!authHelper.verifyPassword(oldPwd, user.getPassword())) {
      messageSource.thrown("e.user.change-pwd.old-pwd-wrong");
    }
    user.setPassword(authHelper.encodePassword(newPwd));
  }

  /**
   * 当前登录用户切换到指定的职务。
   *
   * @param actorId 职务ID
   */
  @Transactional
  public void changeActor(String actorId) {
    User currentUser = getLogonUser();
    Actor actor = actorDao.get(actorId);
    if (!currentUser.getActors().contains(actor)) {
      messageSource.thrown("e.actor.change.exceed");
    }
    currentUser.setDefaultActor(actor);

    AuthToken authToken = new AuthToken(currentUser.getUsername(), actor.getRole().getPrivilegs());
    authHelper.applyToken(authToken);
  }

  /**
   * 获取当前登录用户机构。
   * 
   * @return 返回当前登录用户机构。
   */
  public Organ getCurrentOrgan() {
    return getLogonUser().getDefaultActor().getOrgan();
  }
}
