package com.test.service;

import com.test.constant.Entity;
import io.vertx.core.Vertx;

public class DepartmentService implements Services {

  private final Vertx vertx;

  public DepartmentService(Vertx vertx) {
    this.vertx = vertx;
  }

  @Override
  public Vertx vertx() {
    return vertx;
  }

  @Override
  public Entity collection() {
    return Entity.DEPARTMENT;
  }
}
