package com.employee.routers;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import java.util.List;

public class WorkRouter extends AbstractVerticle {

  private Router workSubRouter;

  public WorkRouter(Router workSubRouter) {
    this.workSubRouter = workSubRouter;
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    workRoutes();
  }

  private void workRoutes() {
    workSubRouter.route("/work").method(HttpMethod.POST).handler(this::addWork);
    workSubRouter.route("/work/:id").method(HttpMethod.GET).handler(this::getWork);
    workSubRouter.route("/work/:id").method(HttpMethod.DELETE).handler(this::deleteWork);
    workSubRouter.route("/works").method(HttpMethod.GET).handler(this::getAllWork);
    workSubRouter.route("/work/:id").method(HttpMethod.PUT).handler(this::updateWork);
  }

  private void updateWork(RoutingContext routingContext) {
    JsonObject jsonObject = routingContext.body().asJsonObject();
    int id =Integer.parseInt(routingContext.pathParam("id"));
    JsonObject updateJson = new JsonObject().put("id",id).put("jsonobject",jsonObject);
    vertx.eventBus().request("work.service.update",updateJson).onSuccess(success->{

      JsonObject object = new JsonObject().put("status", "Document updated");
      routingContext.response().putHeader("content-type", "application/json");
      routingContext.response().end(object.encodePrettily());
    }).onFailure(failure->{
      JsonObject object = new JsonObject().put("status", "Update failed").put("cause",failure.getMessage());
      routingContext.response().putHeader("content-type", "application/json");
      routingContext.response().end(object.encodePrettily());
    });
  }

  private void getAllWork(RoutingContext routingContext) {
    vertx.eventBus().<JsonArray>request("work.service.get.all",null, result->{
      if(result.succeeded())
      {
        JsonArray jsonArray  = result.result().body();
        routingContext.response().putHeader("content-type", "application/json");
        routingContext.response().end(jsonArray.encodePrettily());
      }
    });
  }

  private void deleteWork(RoutingContext routingContext) {
    int id =Integer.parseInt(routingContext.pathParam("id"));
    vertx.eventBus().<Boolean>request("work.service.delete",id, handle->{
      if(handle.succeeded())
      {
        boolean result = handle.result().body();
        if(result)
        {
          JsonObject returnResponse = new JsonObject().put("result", "Record deleted");
          routingContext.response().putHeader("content-type", "application/json");
          routingContext.response().end(returnResponse.encodePrettily());
        } else {
          JsonObject returnResponse = new JsonObject().put("result", "Record not deleted !!");
          routingContext.response().putHeader("content-type", "application/json");
          routingContext.response().end(returnResponse.encodePrettily());
        }
      }
    });
  }

  private void getWork(RoutingContext routingContext) {
    int id = Integer.parseInt(routingContext.pathParam("id"));
    vertx.eventBus().<JsonObject>request("work.service.get",id,handle->{
      if(handle.succeeded())
      {
        JsonObject object = handle.result().body();
        routingContext.response().putHeader("content-type", "application/json");
        routingContext.response().end(object.encodePrettily());
      }
    });
  }


  private void addWork(RoutingContext routingContext) {
   JsonObject jsonObject =  routingContext.body().asJsonObject();
    vertx.eventBus().<JsonObject>request("work.service.add",jsonObject,handle->{
      if(handle.succeeded())
      {
        JsonObject responseJsonObject = handle.result().body();
        routingContext.response().putHeader("content-type", "application/json");
        routingContext.response().end(responseJsonObject.encodePrettily());
      }
    });
  }
}
