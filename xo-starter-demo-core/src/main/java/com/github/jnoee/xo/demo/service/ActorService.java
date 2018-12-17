package com.github.jnoee.xo.demo.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.jnoee.xo.demo.entity.Actor;
import com.github.jnoee.xo.jpa.audit.annotation.SimpleLog;
import com.github.jnoee.xo.jpa.dao.Dao;
import com.github.jnoee.xo.message.MessageSource;
import com.github.jnoee.xo.utils.BeanUtils;

/**
 * 职务管理。
 */
@Service
public class ActorService {
  @Resource
  private Dao<Actor> actorDao;
  @Resource
  private MessageSource messageSource;

  @Transactional(readOnly = true)
  public Actor get(String actorId) {
    return actorDao.get(actorId);
  }

  @Transactional
  @SimpleLog(code = "l.actor.add", vars = {"actor.user.name", "actor.name"})
  public void create(Actor actor) {
    actorDao.save(actor);
  }

  @Transactional
  @SimpleLog(code = "l.actor.edit", vars = {"actor.user.name", "actor.name"})
  public void update(Actor actor) {
    Actor origActor = get(actor.getId());
    BeanUtils.copyFields(actor, origActor);
  }

  @Transactional
  @SimpleLog(code = "l.actor.del", vars = {"actor.user.name", "actor.name"})
  public void delete(Actor actor) {
    if (actor.isDefaultActor()) {
      messageSource.thrown("e.actor.del.is-default");
    }
    actorDao.remove(actor);
  }

  /**
   * 设置指定的职务为默认职务。
   * 
   * @param actor 职务
   */
  @Transactional
  @SimpleLog(code = "l.actor.set-default", vars = {"actor.user.name", "actor.name"})
  public void setDefault(Actor actor) {
    actor.getUser().setDefaultActor(actor);
  }
}
