package com.employee.data;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class DbVerticle extends AbstractVerticle {

List<JsonObject>  store = new ArrayList<>();


  @Override
  public void start(Promise<Void> startPromise) throws Exception  {
    startPromise.complete();
    vertx.eventBus().consumer("db.save").handler(this::save);
    vertx.eventBus().consumer("db.find").handler(this::find);
    vertx.eventBus().consumer("db.findAll").handler(this::findAll);
    vertx.eventBus().consumer("db.delete").handler(this::delete);
    vertx.eventBus().<JsonObject>consumer("db.update").handler(this::update);
  }

  private void update(Message<JsonObject> objectMessage) {
    JsonObject jsonObject = objectMessage.body();
    for(JsonObject object : store)
    {
      if(object.getInteger("id").equals(jsonObject.getInteger("id")))
      {

        store.remove(object);
        object=jsonObject;
        store.add(object);
        objectMessage.reply(object);
        break;
      }
    }
  }

  private void delete(Message<Object> objectMessage)
  {
    int id = (int) objectMessage.body();
    for(int i=0;i<store.size();i++)
    {
      JsonObject jsonObject = store.get(i);
      if(jsonObject.getInteger("id")==(id))
      {
        store.remove(jsonObject);
        objectMessage.reply(true);
        break;
      }
    }
  }
  private void findAll(Message<Object> objectMessage) {

    JsonArray jsonArray = new JsonArray();
    for(JsonObject jsonObject : store)
    {
      jsonArray.add(jsonObject);
    }
    objectMessage.reply(jsonArray);
  }

  public void save(Message<Object> message)
  {
    JsonObject jsonObject = (JsonObject) message.body();
    store.add(jsonObject);
    message.reply(jsonObject);
  }

  public void find(Message<Object> message)
  {
    int index = (int) message.body();
    JsonObject returnJsonObject=null;
    for(JsonObject object :store)
    {
      int id = object.getInteger("id");
      if(id==index)
      {
        returnJsonObject = object;
        break;
      }
    }
    message.reply(returnJsonObject);
  }
}
