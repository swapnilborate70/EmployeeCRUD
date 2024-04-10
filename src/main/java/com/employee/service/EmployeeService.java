package com.employee.service;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class EmployeeService extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    eventBusMessages();
  }

  private void eventBusMessages() {
    vertx.eventBus().<JsonObject>consumer("emp.service.add").handler(this::handleAddEmployee);
    vertx.eventBus().<Integer>consumer("emp.service.get").handler(this::handleGetEmployee);
    vertx.eventBus().consumer("emp.service.getall").handler(this::handleGetAllEmployee);
    vertx.eventBus().consumer("emp.service.delete").handler(this::handleDeleteEmployee);
    vertx.eventBus().<JsonObject>consumer("emp.service.update").handler(this::handleUpdateEmployee);
    vertx.eventBus().<Integer>consumer("emp.service.empworks").handler(this::handleGetWorksOfEmp);
  }

  private void handleGetWorksOfEmp(Message<Integer> integerMessage) {
    int id = integerMessage.body();
    vertx.eventBus().<JsonArray>request("db.work.getworks.emp",id).onSuccess(succ->{
      JsonArray jsonArray = succ.body();
      integerMessage.reply(jsonArray);
    }).onFailure(fail->{
      String msg = fail.getMessage();
      integerMessage.reply(msg);
    });
  }

  private void handleUpdateEmployee(Message<JsonObject> objectMessage) {
    JsonObject updated = objectMessage.body();

    vertx.eventBus().<JsonObject>request("db.update",updated, handle->{
      if(handle.succeeded())
      {
       JsonObject jsonObject =  handle.result().body();
       objectMessage.reply(jsonObject);
      }
    });
  }

  private void handleDeleteEmployee(Message<Object> objectMessage) {
    vertx.eventBus().request("db.delete",objectMessage.body(),handle->{
      if(handle.succeeded())
      {
        objectMessage.reply(handle.result().body());
      } else {
        objectMessage.reply(handle.cause());
      }
    });
  }

  private void handleGetAllEmployee(Message<Object> objectMessage) {
    vertx.eventBus().<JsonArray>request("db.find.all",null, handle->{
      JsonArray jsonArray = handle.result().body();
      objectMessage.reply(jsonArray);
    });
  }

  private void handleGetEmployee(Message<Integer> integerMessage) {
    vertx.eventBus().<JsonObject>request("db.find",integerMessage.body().intValue(),handler->{
      if(handler.succeeded())
      {
        JsonObject jsonObject = handler.result().body();
        integerMessage.reply(jsonObject);
      } else {
        integerMessage.reply(handler.cause());
      }
    });
  }

  private void handleAddEmployee(Message<JsonObject> objectMessage) {
    JsonObject object = objectMessage.body();
    vertx.eventBus().<JsonObject>request("db.save",object,event -> {
      if(event.succeeded())
      {
        JsonObject jsonObject =  event.result().body();
        objectMessage.reply(jsonObject);
      }
    });
  }


}
