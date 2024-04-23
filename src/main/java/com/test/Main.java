package com.test;

import com.test.verticle.API;
import com.test.verticle.Database;
import io.vertx.core.Vertx;

public class Main  {
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new API());
    vertx.deployVerticle(new Database());
  }
}
