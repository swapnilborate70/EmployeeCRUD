package com.employee.routers;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import java.util.List;
import java.util.Objects;

public class RoutersVerticle extends AbstractVerticle {

  private final Router subRouter;

  public RoutersVerticle(Router router) {
    this.subRouter = router;
  }


  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    startPromise.complete();
    subRouter.route("/employee").method(HttpMethod.POST).handler(this::addEmployeeHandler);
    subRouter.route("/employee/:id").method(HttpMethod.GET).handler(this::getEmployeeHandler);
    subRouter.route("/employees").method(HttpMethod.GET).handler(this::getAllEmployeeHandler);
    subRouter.route("/employee/:id").method(HttpMethod.DELETE).handler(this::deleteEmployeeHandler);
    subRouter.route("/employee/:id").method(HttpMethod.PUT).handler(this::updateEmployee);
  }

  private void updateEmployee(RoutingContext routingContext) {
    int id = Integer.parseInt(routingContext.pathParam("id"));
    JsonObject object = routingContext.body().asJsonObject();
    JsonObject updateDetails = new JsonObject().put("id",id).put("jsonobject",object);
    vertx.eventBus().request("service.update",updateDetails,req->{
      if(req.succeeded())
      {
        routingContext.response().putHeader("content-type", "application/json");
        routingContext.response().end(new JsonObject().put("status","Record Updated").encode());
      }
    });
  }

  public void deleteEmployeeHandler(RoutingContext routingContext)
  {
    int id = Integer.parseInt(routingContext.pathParam("id"));
    vertx.eventBus().request("service.delete",id,req->{
      if(req.succeeded()){
        routingContext.response().putHeader("content-type", "application/json");
        routingContext.response().end(new JsonObject().put("status","Deleted successfully").encode());
      } else {
        routingContext.response().putHeader("content-type", "application/json");
        routingContext.response().end(new JsonObject().put("status","not deleted successfully").encode());

      }
    });
  }

  public void getAllEmployeeHandler(RoutingContext routingContext)
  {
    vertx.eventBus().request("service.get.all","", req->{

      Object object = req.result().body();
      JsonArray object1 = JsonArray.of(object);
      routingContext.response().putHeader("content-type", "application/json");

      routingContext.response().end(object1.encodePrettily());
    });
  }


  public void addEmployeeHandler(RoutingContext routingContext) {
    JsonObject body = routingContext.body().asJsonObject();

    vertx.eventBus().request("service.add", body, reply -> {
      if (reply.succeeded()) {
        JsonObject jsonObject = (JsonObject) reply.result().body();
        routingContext.response().putHeader("content-type", "application/json");

        routingContext.response().end(jsonObject.encode());
      }
    });
  }

  public void getEmployeeHandler(RoutingContext routingContext) {
    int id = Integer.parseInt(routingContext.pathParam("id"));
    vertx.eventBus().request("service.get", id, reply -> {
      if (reply.succeeded()) {
        JsonObject jsonObject = (JsonObject) reply.result().body();
        if(jsonObject !=null)
        {
          routingContext.response().putHeader("content-type", "application/json");

          routingContext.response().end(jsonObject.encode());
        } else {
          routingContext.response().putHeader("content-type", "application/json");

          routingContext.response().end(new JsonObject().put("status","not found").encode());
        }

      }
    });
  }


}
