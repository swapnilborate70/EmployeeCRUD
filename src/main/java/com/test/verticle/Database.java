package com.test.verticle;

import com.test.constant.Address;
import com.test.constant.Constant;
import com.test.constant.Entity;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.IndexModel;
import io.vertx.ext.mongo.IndexOptions;
import io.vertx.ext.mongo.MongoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class Database extends AbstractVerticle {

  private static final Logger logger = LoggerFactory.getLogger(Database.class);
  private MongoClient mongoClient;


  @Override
  public void start(Promise<Void> startPromise) throws Exception {

      vertx.fileSystem().readFile(Constant.FILE_PATH, dbConfig -> {
        if (dbConfig.succeeded()) {
          JsonObject mongoConfig = dbConfig.result().toJsonObject().getJsonObject(Constant.DB_CONFIG_FILE_KEY);
          this.mongoClient = MongoClient.create(vertx, mongoConfig);
          System.out.println("Database config read success");
          logger.info("Database config read success");

          createIndexesForEmployee();
          createIndexesForDepartment();

          vertx.eventBus().localConsumer(Address.CREATE.address(), this::create);
          vertx.eventBus().localConsumer(Address.FIND.address(),this::get);
          vertx.eventBus().localConsumer(Address.FIND_ALL.address(),this::getAll);
          vertx.eventBus().localConsumer(Address.DELETE.address(),this::delete);
          vertx.eventBus().localConsumer(Address.UPDATE.address(),this::update);
          startPromise.complete();
        }
        else
        {
          startPromise.fail(dbConfig.cause());
        }
      });
  }

  private void update(Message<JsonObject> message) {
    String collection = message.body().getString(Constant.COLLECTION);
    int id = message.body().getInteger(Constant.ID);
    JsonObject document = message.body().getJsonObject(Constant.DOCUMENT);

    mongoClient.findOneAndUpdate(collection,JsonObject.of(Constant.ID,id),JsonObject.of(Constant.UPDATE_QUERY,document))
      .onSuccess(message::reply)
      .onFailure(fail -> message.fail(500,fail.getMessage()));
  }

  private void delete(Message<JsonObject> message) {
    String collection = message.body().getString(Constant.COLLECTION);
    int id = message.body().getInteger(Constant.ID);

    mongoClient.findOneAndDelete(collection,JsonObject.of(Constant.ID,id))
      .onSuccess(message::reply)
      .onFailure(fail -> message.fail(500,fail.getMessage()));
  }

  private void get(Message<JsonObject> message) {
    int id = message.body().getInteger(Constant.ID);
    String collection = message.body().getString(Constant.COLLECTION);


    mongoClient.findOne(collection,JsonObject.of(Constant.ID,id),null)
      .onSuccess(message::reply)
      .onFailure(fail -> message.fail(500,fail.getCause().getMessage()));
  }

  private void getAll(Message<JsonObject> message) {

    String collection = message.body().getString(Constant.COLLECTION);

    mongoClient.find(collection,JsonObject.of())
      .onSuccess(documents->{
        JsonArray documentsInArray = new JsonArray();
        for(JsonObject document : documents)
        {
          documentsInArray.add(document);
        }
        message.reply(documentsInArray);
      })
      .onFailure(fail -> message.fail(500, fail.getMessage()));
  }

  private void create(Message<JsonObject> message) {
    JsonObject messageBody = message.body();

    JsonObject document = messageBody.getJsonObject(Constant.DOCUMENT);
    String collection = messageBody.getString(Constant.COLLECTION);

    mongoClient.insert(collection,document)
      .onSuccess(message::reply)
      .onFailure(fail-> message.fail(500,fail.getMessage()));
  }

  void createIndexesForEmployee() {

    //indexing for empID
    JsonObject keysEmpID = new JsonObject().put(Constant.EMP_ID, 1);
    IndexOptions optionsEmpID = new IndexOptions().unique(true);
    IndexModel modelEmpID = new IndexModel(keysEmpID,optionsEmpID);

    mongoClient.createIndexes(Entity.EMPLOYEE.getName(), List.of(modelEmpID))
      .onSuccess(success->{
        System.out.println("Index created for Employee");
        logger.info("Index created for Employee");
      });
  }

  void createIndexesForDepartment() {

    //indexing for deptID
    JsonObject keysDeptID = new JsonObject().put(Constant.DEPT_ID, 1);
    IndexOptions optionsDeptID = new IndexOptions().unique(true);

    IndexModel modelDeptID = new IndexModel(keysDeptID,optionsDeptID);

    mongoClient.createIndexes(Entity.DEPARTMENT.getName(), List.of(modelDeptID))
      .onSuccess(success->{
        System.out.println("Index created for Department");
        logger.info("Index created for Department");
      });
  }
}

