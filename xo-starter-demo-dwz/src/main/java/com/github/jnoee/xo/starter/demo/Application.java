package com.github.jnoee.xo.starter.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.github.jnoee.xo.jpa.dao.DaoScan;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan
@DaoScan
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
