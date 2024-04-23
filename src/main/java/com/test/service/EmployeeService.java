package com.test.service;

import com.test.constant.Collection;
import io.vertx.core.Vertx;

public class EmployeeService implements Services{

  private final Vertx vertx;

    public EmployeeService(Vertx vertx) {
        this.vertx = vertx;
    }

    @Override
    public Vertx vertx() {
    return vertx;
  }

  @Override
  public Collection collection() {
    return Collection.EMPLOYEE;
  }
}
