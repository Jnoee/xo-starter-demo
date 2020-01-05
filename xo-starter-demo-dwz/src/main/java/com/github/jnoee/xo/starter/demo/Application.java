package com.github.jnoee.xo.starter.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.github.jnoee.xo.jpa.dao.DaoScan;

@SpringBootApplication
@EnableJpaRepositories
@DaoScan
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
