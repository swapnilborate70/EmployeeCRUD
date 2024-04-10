package com.employee.repo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

import java.util.ArrayList;
import java.util.List;

public class WorkDB extends AbstractVerticle {

  private MongoClient mongoClient;

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    io.vertx.core.json.JsonObject config = new io.vertx.core.json.JsonObject().put("url", "mongodb://localhost:27017")
      .put("db_name", "NewEmployee");

    mongoClient = MongoClient.create(vertx, config);
    routes();
  }

  private void routes() {
    vertx.eventBus().<JsonObject>consumer("db.work.add").handler(this::addWork);
    vertx.eventBus().<Integer>consumer("db.work.get").handler(this::getWork);
    vertx.eventBus().<Integer>consumer("db.work.delete").handler(this::delete);
    vertx.eventBus().<Void>consumer("db.work.get.all").handler(this::getAll);
    vertx.eventBus().<JsonObject>consumer("db.work.update").handler(this::update);

    vertx.eventBus().<Integer>consumer("db.work.emp.works").handler(this::getWorksOfEmp);
  }

  private void getWorksOfEmp(Message<Integer> integerMessage) {
    int empId = integerMessage.body();

    mongoClient.find("work",new JsonObject().put("empId",empId),handle->{
      List<JsonObject> list = handle.result();
      JsonArray jsonArray = new JsonArray();
      for(JsonObject jsonObject : list)
      {
        jsonArray.add(jsonObject);
      }
      integerMessage.reply(jsonArray);
    });
  }


  private void update(Message<JsonObject> message) {
    JsonObject jsonObject =  message.body();
    int id = jsonObject.getInteger("id");
    JsonObject updatedJsonObject = jsonObject.getJsonObject("jsonobject");
    mongoClient.findOneAndUpdate("work",new JsonObject().put("_id",id),new JsonObject().put("$set",updatedJsonObject)).onSuccess(success->{
      JsonObject object = success;
      if(object!=null)
      {
        message.reply(object);
      }
      else {
        message.fail(404,"not found document with provided ID ");
      }
    });
  }

  private void getAll(Message<Void> voidMessage) {
    mongoClient.find("work",new JsonObject(),result->{
      if(result.succeeded())
      {
        List<JsonObject> list = result.result();
        JsonArray jsonArray = new JsonArray();
        for(JsonObject jsonObject : list)
        {
          jsonArray.add(jsonObject);
        }
        voidMessage.reply(jsonArray);
      }
    });
  }


  private void delete(Message<Integer> integerMessage) {
    mongoClient.findOneAndDelete("work",new JsonObject().put("_id",integerMessage.body()),result->{
      if (result.succeeded())
      {
           JsonObject object =  result.result();
           if(object!=null)
           {
             integerMessage.reply(true);
           } else integerMessage.reply(false);
      }
    });
  }

  private void getWork(Message<Integer> objectMessage) {
    int id = objectMessage.body();
    mongoClient.findOne("work",new JsonObject().put("_id",id),null,handle->{
      if(handle.succeeded())
      {
        JsonObject object = handle.result();
        objectMessage.reply(object);
      }
    });
  }

  private void addWork(Message<JsonObject> objectMessage) {
    JsonObject jsonObject = objectMessage.body();
    mongoClient.insert("work",jsonObject, handle->{
      if(handle.succeeded())
      {
        String response = handle.result();
        if(response==null)
        {
          JsonObject jsonreply = new JsonObject().put("status","success").put("result", jsonObject);
          objectMessage.reply(new JsonObject().put("result",jsonreply));
        } else {
          objectMessage.reply(new JsonObject().put("result",response));
        }
      }
    });
  }
}
