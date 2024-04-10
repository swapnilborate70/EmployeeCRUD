package com.employee.routers;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class EmployeeRouter extends AbstractVerticle {
  Router empSubRouter;

  public EmployeeRouter(Router empSubRouter) {
    this.empSubRouter = empSubRouter;
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
      getRoutes();
  }

  public void getRoutes()
  {
      empSubRouter.route("/employee").method(HttpMethod.POST).handler(this::addEmployeeHandler);
      empSubRouter.route("/employee/:id").method(HttpMethod.GET).handler(this::getEmployeeHandler);
      empSubRouter.route("/employees").method(HttpMethod.GET).handler(this::getAllEmployee);
      empSubRouter.route("/employee/:id").method(HttpMethod.DELETE).handler(this::deleteEmployeeHandler);
      empSubRouter.route("/employee/:id").method(HttpMethod.PUT).handler(this::updateEmployeeHandler);

      empSubRouter.route("/employee-works/:id").method(HttpMethod.GET).handler(this::getWorksOfEmployee);
  }

  private void getWorksOfEmployee(RoutingContext routingContext) {
    int id =Integer.parseInt(routingContext.pathParam("id"));
    vertx.eventBus().<JsonArray>request("emp.service.empworks",id,handle->{
      if(handle.succeeded())
      {
        JsonArray jsonArray = handle.result().body();
        routingContext.response().putHeader("content-type", "application/json");
        routingContext.response().end(jsonArray.encodePrettily());
      } else {
        JsonObject jsonObject = new JsonObject().put("status","failed").put("cause",handle.cause());
        routingContext.response().putHeader("content-type", "application/json");
        routingContext.response().end(jsonObject.encodePrettily());
      }
    });
  }

  private void updateEmployeeHandler(RoutingContext routingContext) {
    JsonObject jsonObject = routingContext.body().asJsonObject();
    int id = Integer.parseInt(routingContext.pathParam("id"));

    JsonObject body = new JsonObject().put("_id",id).put("object",jsonObject);

    vertx.eventBus().<JsonObject>request("emp.service.update",body,handle->{
      if(handle.succeeded())
      {
        JsonObject objectResponse = handle.result().body();
        routingContext.response().putHeader("content-type", "application/json");
        routingContext.response().end(objectResponse.encodePrettily());
      }
    });
  }

  private void deleteEmployeeHandler(RoutingContext routingContext) {
    int id =Integer.parseInt(routingContext.pathParam("id"));
    vertx.eventBus().request("emp.service.delete",id,handle->{
      if(handle.succeeded())
      {
        routingContext.response().putHeader("content-type", "application/json");
        routingContext.response().end(new JsonObject().put("status","deleted").encodePrettily());
      } else {
        routingContext.response().putHeader("content-type", "application/json");
        routingContext.response().end(new JsonObject().put("status",handle.cause()).encodePrettily());
      }
    });

  }

  private void getAllEmployee(RoutingContext routingContext) {
    vertx.eventBus().<JsonArray>request("emp.service.getall",null,handle->{
      if(handle.succeeded())
      {
        JsonArray jsonArray = handle.result().body();
        routingContext.response().putHeader("content-type", "application/json");
        routingContext.response().end(jsonArray.encodePrettily());
      } else if(handle.failed()){
        routingContext.response().putHeader("content-type", "application/json");
        routingContext.response().end(new JsonObject().put("status",handle.cause()).encodePrettily());
      }
    });
  }

  private void getEmployeeHandler(RoutingContext routingContext) {
    int id = Integer.parseInt(routingContext.pathParam("id"));
    vertx.eventBus().<JsonObject>request("emp.service.get",id,res->{
      if(res.succeeded())
      {
        JsonObject jsonObject = res.result().body();
        routingContext.response().putHeader("content-type", "application/json");
        routingContext.response().end(jsonObject.encodePrettily());
      } else {
        routingContext.response().putHeader("content-type", "application/json");
        routingContext.response().end(new JsonObject().put("status",res.cause()).encodePrettily());
      }
    });
  }

  private void addEmployeeHandler(RoutingContext routingContext) {
    JsonObject jsonObject = routingContext.body().asJsonObject();

    vertx.eventBus().<JsonObject>request("emp.service.add",jsonObject,event -> {
      if(event.succeeded())
      {
        JsonObject object = event.result().body();

        routingContext.response().putHeader("content-type", "application/json");
        routingContext.response().end(object.encodePrettily());
      } else {
        routingContext.response().setStatusCode(404).end();
      }

    });
  }
}
