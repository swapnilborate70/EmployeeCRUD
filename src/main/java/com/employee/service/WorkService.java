package com.employee.service;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;


public class WorkService extends AbstractVerticle {


  @Override
  public void start(Promise<Void> startPromise) throws Exception {
      routes();
  }

  private void routes() {
    vertx.eventBus().<JsonObject>consumer("work.service.add").handler(this::addWorkHandler);
    vertx.eventBus().<Integer>consumer("work.service.get").handler(this::getWorkHandler);
    vertx.eventBus().<Integer>consumer("work.service.delete").handler(this::deleteHandler);
    vertx.eventBus().<Void>consumer("work.service.get.all").handler(this::getAll);
    vertx.eventBus().<JsonObject>consumer("work.service.update").handler(this::update);
    vertx.eventBus().<Integer>consumer("db.work.getworks.emp").handler(this::getWorksOfEmp);
  }

  private void getWorksOfEmp(Message<Integer> integerMessage) {
    int id = integerMessage.body();
    vertx.eventBus().<JsonArray>request("db.work.emp.works",id,result->{
      if(result.succeeded())
      {
        JsonArray jsonArray = result.result().body();
        integerMessage.reply(jsonArray);
      }
    });
  }

  private void update(Message<JsonObject> message) {
    JsonObject object = message.body();
    vertx.eventBus().<JsonObject>request("db.work.update",object).onSuccess(success->{
      message.reply(success.body());
    }).onFailure(failure->{
      message.fail(404,failure.getMessage());

    });
  }

  private void getAll(Message<Void> voidMessage) {
    vertx.eventBus().<JsonArray>request("db.work.get.all",null, result->{
      if(result.succeeded())
      {
        JsonArray  jsonArray = result.result().body();
        voidMessage.reply(jsonArray);
      }
    });
  }

  private void deleteHandler(Message<Integer> integerMessage) {
    int id  = integerMessage.body();
    vertx.eventBus().<Boolean>request("db.work.delete", id , handle->{
      if(handle.succeeded())
      {
        boolean rst = handle.result().body();
        integerMessage.reply(rst);
      }
    });
  }

  private void getWorkHandler(Message<Integer> integerMessage) {
    int id = integerMessage.body();
    vertx.eventBus().<JsonObject>request("db.work.get",id,handle->{
      if(handle.succeeded())
      {
        integerMessage.reply(handle.result().body());
      }
    });

  }

  private void addWorkHandler(Message<JsonObject> jsonObjectMessage) {
    JsonObject jsonObject = jsonObjectMessage.body();
    vertx.eventBus().<JsonObject>request("db.work.add",jsonObject,handle->{
      if(handle.succeeded())
      {
        JsonObject object = handle.result().body();
        jsonObjectMessage.reply(object);
      }
    });
  }
}
