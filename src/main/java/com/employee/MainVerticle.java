package com.employee;
import com.employee.routers.EmployeeRouter;
import com.employee.service.EmployeeService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainVerticle extends AbstractVerticle {

  private EmployeeService employeeService;

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    Router router = Router.router(vertx);

    router.route().handler(BodyHandler.create());


    EmployeeRouter employeeRouter = new EmployeeRouter(vertx);

    router.route("/api/*").subRouter(employeeRouter.getRouter());

    vertx.createHttpServer().requestHandler(router).listen(9090).onSuccess(httpServer -> {
      System.out.println("Server started on : "+httpServer.actualPort());
      startPromise.complete();
    }).onFailure(throwable -> {
      System.out.println(throwable.getMessage());
      startPromise.fail(throwable);
    });
  }



  public static void main(String[] args) {
    Vertx vertx1 = Vertx.vertx();
    vertx1.deployVerticle(new MainVerticle());
  }
}
