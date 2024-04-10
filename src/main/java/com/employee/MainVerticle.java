package com.employee;

import com.employee.repo.EmployeeDB;
import com.employee.repo.WorkDB;
import com.employee.routers.EmployeeRouter;
import com.employee.routers.WorkRouter;
import com.employee.service.EmployeeService;
import com.employee.service.WorkService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class MainVerticle extends AbstractVerticle {

  public static void main(String[] args) {
    Vertx vertx1 = Vertx.vertx();
    vertx1.deployVerticle(new MainVerticle());
    //EventBus eventBus = vertx1.eventBus();
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());

    Router empSubRouter = Router.router(vertx);
    Router workSubRouter = Router.router(vertx);

    router.route("/api/emp/*").subRouter(empSubRouter);
    router.route("/api/work/*").subRouter(workSubRouter);

    vertx.deployVerticle(new EmployeeRouter(empSubRouter));
    vertx.deployVerticle(new EmployeeService());
    vertx.deployVerticle(new EmployeeDB());
    vertx.deployVerticle(new WorkService());
    vertx.deployVerticle(new WorkRouter(workSubRouter));
    vertx.deployVerticle(new WorkDB());

    vertx.createHttpServer().requestHandler(router).listen(8888, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port : "+http.result().actualPort());
      } else {
        startPromise.fail(http.cause());
      }
    });
  }
}
