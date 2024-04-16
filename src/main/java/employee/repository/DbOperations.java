package employee.repository;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

import java.util.List;

public class  DbOperations{

  public Future<String> create(JsonObject jsonObject, MongoClient mongoClient, String collectionName) {
    return mongoClient.insert(collectionName,jsonObject);
  }

  public Future<JsonObject> find(int id, MongoClient mongoClient, String collectionName) {
    return mongoClient.findOne(collectionName, new JsonObject().put("_id", id), null);
  }

  public Future<List<JsonObject>> findAll(MongoClient mongoClient, String collectionName)
  {
    return mongoClient.find(collectionName, new JsonObject());
  }

  public Future<JsonObject> delete(int id, MongoClient mongoClient, String collectionName)
  {
    return mongoClient.findOneAndDelete(collectionName,new JsonObject().put("_id",id));
  }

  public Future<JsonObject> update(JsonObject updatedJsonObject, int id, MongoClient mongoClient, String collectionName)
  {
    return mongoClient.findOneAndUpdate(collectionName, new JsonObject().put("_id",id),new JsonObject().put("$set",updatedJsonObject));
  }

}
