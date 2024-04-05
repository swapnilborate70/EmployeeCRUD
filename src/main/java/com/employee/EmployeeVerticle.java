package com.employee;

import com.employee.data.DbConnect;
import com.employee.data.DbVerticle;
import com.employee.routers.RoutersVerticle;
import com.employee.service.ServiceVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class EmployeeVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    Router router = Router.router(vertx);

    router.route().handler(BodyHandler.create());

    Router subRouter  = Router.router(vertx);

    router.route("/api/*").subRouter(subRouter);

    vertx.deployVerticle(new DbVerticle());
    vertx.deployVerticle(new ServiceVerticle());
    vertx.deployVerticle(new RoutersVerticle(subRouter));
    vertx.deployVerticle(new DbConnect());


    vertx.createHttpServer().requestHandler(router).listen(8888, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port 8888");
      } else {
        startPromise.fail(http.cause());
      }
    });
  }

  public static void main(String[] args) {
    Vertx vertex = Vertx.vertx();
    vertex.deployVerticle(new EmployeeVerticle());
  }
}
