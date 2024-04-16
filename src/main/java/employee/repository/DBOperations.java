package employee.repository;

import employee.AddressBook;
import employee.Constant;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

public class DBOperations extends AbstractVerticle {

  private MongoClient mongoClient;

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    // todo: to be captured from Config File! How?
    vertx.executeBlocking(() -> MongoClient.create(vertx, JsonObject.of("connection_string", "mongodb://aiops:AIOps%40123@localhost:9999/", "db_name", "swapnil")))
      .onSuccess(client -> {
        mongoClient = client;

        // todo: Question: Difference between consumer and localConsumer?
        vertx.eventBus().localConsumer(AddressBook.CREATE.address(), this::create);
        vertx.eventBus().localConsumer(AddressBook.FIND_ALL.address(), this::findAll);

        startPromise.complete();
      })
      .onFailure(startPromise::fail);
  }

  public void create(Message<JsonObject> message) {

    String collection = message.body().getString(Constant.COLLECTION);
    JsonObject document = message.body().getJsonObject(Constant.DOCUMENT);

    // todo: user logger
    mongoClient.insert(collection, document)
      .onSuccess(message::reply)
      .onFailure(e -> {
        e.printStackTrace();      // todo: user logger
        message.fail(500, e.getMessage());
      });
  }

  private void findAll(Message<String> message) {
    mongoClient.find(message.body(), JsonObject.of())
      .onSuccess(message::reply)
      .onFailure(e -> {
        e.printStackTrace();      // todo: user logger
        message.fail(500, e.getMessage());
      });
  }

  public Future<JsonObject> find(String collection, int id) {
    // TODO: To Be Implemented
    return mongoClient.findOne(collection, JsonObject.of(Constant.ID, id), null);
  }


  public Future<JsonObject> delete(String collection, int id) {
    // TODO: To Be Implemented
    return mongoClient.findOneAndDelete(collection, JsonObject.of(Constant.ID, id));
  }

  public Future<JsonObject> update(String collection, int id, JsonObject document) {
    // TODO: To Be Implemented
    return mongoClient.findOneAndUpdate(collection, new JsonObject().put(Constant.ID, id), JsonObject.of("$set", document));
  }

}
