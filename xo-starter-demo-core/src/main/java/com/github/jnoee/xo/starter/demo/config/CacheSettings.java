package com.github.jnoee.xo.starter.demo.config;

import org.springframework.stereotype.Component;

import com.github.jnoee.xo.cache.config.AbstractCacheSettings;

@Component("com.github.jnoee.xo.starter.demo.config.CacheSettings")
public class CacheSettings extends AbstractCacheSettings {
  public CacheSettings() {
    addRegion("organ", "role", "user", "actor");
  }
}
