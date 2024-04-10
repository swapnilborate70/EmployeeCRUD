package com.employee.repo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

import java.util.List;

public class EmployeeDB extends AbstractVerticle {

  private MongoClient mongoClient;

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    JsonObject config = new JsonObject().put("url", "mongodb://localhost:27017")
      .put("db_name", "NewEmployee");

    mongoClient = MongoClient.create(vertx, config);
    eventBusMessages();
  }


  public void eventBusMessages()
  {
    vertx.eventBus().<JsonObject>consumer("db.save").handler(this::save);
    vertx.eventBus().<Integer>consumer("db.find").handler(this::find);
    vertx.eventBus().consumer("db.find.all").handler(this::findAll);
    vertx.eventBus().consumer("db.delete").handler(this::delete);
    vertx.eventBus().<JsonObject>consumer("db.update").handler(this::update);
  }

  private void update(Message<JsonObject> objectMessage) {
    JsonObject jsonObject = objectMessage.body();

    JsonObject updatedObject = jsonObject.getJsonObject("object");
    int id = jsonObject.getInteger("_id");


    mongoClient.findOneAndUpdate("employee", new JsonObject().put("_id", id), new JsonObject().put("$set",updatedObject),handle->{
      if(handle.succeeded())
      {
        objectMessage.reply(updatedObject);
      }
    });


  }

  private void delete(Message<Object> objectMessage) {
    mongoClient.findOneAndDelete("employee",new JsonObject().put("_id",objectMessage.body()),handle->{
      if(handle.succeeded())
      {
        objectMessage.reply(true);
      } else objectMessage.fail(500, "Internal server error");
    });

  }

  private void findAll(Message<Object> objectMessage) {
    mongoClient.find("employee",new JsonObject(),handle->{
      List<JsonObject> list = handle.result();
      JsonArray jsonArray = JsonArray.of(list);
      objectMessage.reply(jsonArray);
    });
  }

  private void find(Message<Integer> integerMessage) {
    int id = integerMessage.body();
    mongoClient.findOne("employee",new JsonObject().put("id",id),null,handler->{
      if(handler.succeeded())
      {
        JsonObject jsonObject = handler.result();
        integerMessage.reply(jsonObject);
      }
    });
  }

  public void save(Message<JsonObject> message)
  {
      JsonObject object = message.body();
      mongoClient.insert("employee",object,res-> {
        if(res.succeeded())
        {
          String id = res.result();
          message.reply(object);
        } else {
          message.reply(new JsonObject().put("status",res.cause().toString()));
        }
      });
  }
}
