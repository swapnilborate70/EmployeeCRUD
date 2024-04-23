package com.test.service;

import com.test.constant.Collection;
import io.vertx.core.Vertx;

public class DepartmentService implements Services{

  private final Vertx vertx;

  public DepartmentService(Vertx vertx) {
    this.vertx = vertx;
  }

  @Override
  public Vertx vertx() {
    return vertx;
  }

  @Override
  public Collection collection() {
    return Collection.DEPARTMENT;
  }
}
