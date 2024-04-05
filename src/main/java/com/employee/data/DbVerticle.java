package com.employee.data;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DbVerticle extends AbstractVerticle {
  private JsonObject config;

  private MongoClient mongoClient;

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    config = new JsonObject().put("url", "mongodb://localhost:27017")
      .put("db_name", "Employee");

    mongoClient = MongoClient.create(vertx, config);

    vertx.eventBus().consumer("db.save").handler(this::save);
    vertx.eventBus().<Integer>consumer("db.find").handler(this::find);
    vertx.eventBus().consumer("db.findAll").handler(this::findAll);
    vertx.eventBus().<Integer>consumer("db.delete").handler(this::delete);
    vertx.eventBus().<JsonObject>consumer("db.update").handler(this::update);
    startPromise.complete();

  }

  private void update(Message<JsonObject> objectMessage) {

    JsonObject object = objectMessage.body();

    int id = object.getInteger("id");
    JsonObject newBody = object.getJsonObject( "jsonobject");

    mongoClient.findOneAndUpdate("empcollection",new JsonObject().put("id",id),new JsonObject().put("$set",newBody),handle->{
      if(handle.succeeded())
      {
        objectMessage.reply(newBody);
      }
    });
  }

  private void delete(Message<Integer> objectMessage) {
      int id = objectMessage.body().intValue();
      mongoClient.findOneAndDelete("empcollection", new JsonObject().put("id",id),handle->{
        if(handle.succeeded())
        {
          objectMessage.reply(true);
        } else objectMessage.reply(false);
      });

  }

  private void findAll(Message<Object> objectMessage) {
      mongoClient.find("empcollection",new JsonObject(),reply->{

          List<JsonObject> list = reply.result();
          JsonArray jsonArray = new JsonArray(list);
          objectMessage.reply(jsonArray);
      });

  }

  public void save(Message<Object> message) {
    JsonObject jsonObject = (JsonObject) message.body();
    mongoClient.save("empcollection", jsonObject);
    message.reply(jsonObject);
  }

  public void find(Message<Integer> message) {
    int id = message.body().intValue();

    mongoClient.findOne("empcollection", new JsonObject().put("id", id),null,result->{
      JsonObject object = result.result();
      message.reply(object);
    });
  }
}
