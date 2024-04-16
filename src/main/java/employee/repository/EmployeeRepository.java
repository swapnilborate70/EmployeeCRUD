package employee.repository;


import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

import java.util.List;

public class EmployeeRepository {

  private final String collectionName = "empcollection";
  private final MongoClient mongoClient;

  public EmployeeRepository(MongoClient mongoClient) {
    this.mongoClient = mongoClient;
  }

  public Future<String> create(JsonObject jsonObject)
  {
    return mongoClient.insert(collectionName,jsonObject);
  }

  public Future<JsonObject> find(int id) {
    return mongoClient.findOne(collectionName, new JsonObject().put("_id", id), null);
  }

  public Future<List<JsonObject>> findAll()
  {
    return mongoClient.find(collectionName, new JsonObject());
  }

  public Future<JsonObject> delete(int id)
  {
    return mongoClient.findOneAndDelete(collectionName,new JsonObject().put("_id",id));
  }

  public Future<JsonObject> update(JsonObject updatedJsonObject, int id)
  {
    return mongoClient.findOneAndUpdate(collectionName, new JsonObject().put("_id",id),new JsonObject().put("$set",updatedJsonObject));
  }
}
