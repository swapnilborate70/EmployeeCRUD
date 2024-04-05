package com.employee.data;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

public class DbConnect extends AbstractVerticle {


  @Override
  public void start(Promise<Void> startPromise) throws Exception {

//    try {
//
//      JsonObject config = new JsonObject().put("url", "mongodb://localhost:27017")
//        .put("db_name", "Employee");
//
//      MongoClient mongoClient = MongoClient.create(vertx, config);
//      JsonObject document = new JsonObject().put("name", "Swapnil");
//
//      mongoClient.save("empcollection", document).onSuccess(succ -> {
//        System.out.println("success");
//        startPromise.complete();
//      }).onFailure(fail -> {
//        System.out.println("failed");
//        startPromise.fail(fail);
//      });
//
//    }catch (Exception e){
//      System.out.println("exception has occurred");
//    }
//  }
//
  }
}
