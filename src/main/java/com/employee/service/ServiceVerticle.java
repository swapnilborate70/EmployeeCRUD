package com.employee.service;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

public class ServiceVerticle extends AbstractVerticle {



  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    startPromise.complete();
    vertx.eventBus().<JsonObject>consumer("service.add").handler(this::addEmployee);
    vertx.eventBus().<Integer>consumer("service.get").handler(this::getEmployee);
    vertx.eventBus().consumer("service.get.all").handler(this::getAllEmployee);
    vertx.eventBus().<Integer>consumer("service.delete").handler(this::deleteEmployee);
    vertx.eventBus().<JsonObject>consumer("service.update").handler(this::updateEmployee);
  }

  private void updateEmployee(Message<JsonObject> objectMessage) {
    JsonObject object = objectMessage.body();
    vertx.eventBus().request("db.update",object,req->{
      if(req.succeeded())
      {
        objectMessage.reply(true);
      }
    });
  }

  public void deleteEmployee(Message<Integer> objectMessage)
  {
    int id = objectMessage.body().intValue();
    vertx.eventBus().request("db.delete",id,handler->{
      if(handler.succeeded())
      {
        objectMessage.reply(handler.result().body());
      } else objectMessage.reply(false);
    });
  }

  private void getAllEmployee(Message<Object> objectMessage) {

    vertx.eventBus().request("db.findAll","",handler->{
      if(handler.succeeded())
      {
        Object object = handler.result().body();
        objectMessage.reply(object);
      }
    });
  }


  public void addEmployee(Message<JsonObject> message)
  {
    JsonObject body = (JsonObject) message.body();
    vertx.eventBus().request("db.save",body,handler->{
      if(handler.succeeded())
      {
        message.reply(body);
      }else {
        Throwable cause = handler.cause();
        message.fail(500,cause.getMessage());
      }
    });
  }

  public void getEmployee(Message<Integer> message)
  {
    int index = message.body();
    vertx.eventBus().request("db.find",index,handler->{
      if(handler.succeeded())
      {
        JsonObject jsonObject = (JsonObject) handler.result().body();
        message.reply(jsonObject);
      }
    });
  }
}
