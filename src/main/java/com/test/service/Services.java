package com.test.service;

import com.test.constant.Address;
import com.test.constant.Constant;
import com.test.constant.Entity;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public interface Services {

  Vertx vertx();

  Entity collection();

  default Future<Message<String>> create(JsonObject document) {
    return vertx().eventBus().request(Address.CREATE.address(), JsonObject.of(Constant.COLLECTION, collection().getName(), Constant.DOCUMENT, document));
  }

  default Future<Message<JsonObject>> get(int id) {
    return vertx().eventBus().request(Address.FIND.address(), JsonObject.of(Constant.COLLECTION, collection().getName(), Constant.ID, id));
  }

  default Future<Message<JsonArray>> getAll() {
    return vertx().eventBus().request(Address.FIND_ALL.address(), JsonObject.of(Constant.COLLECTION, collection().getName()));
  }

  default Future<Message<JsonObject>> delete(int id) {
    return vertx().eventBus().request(Address.DELETE.address(), JsonObject.of(Constant.COLLECTION, collection().getName(), Constant.ID, id));
  }

  default Future<Message<JsonObject>> update(int id, JsonObject document) {
    return vertx().eventBus().request(Address.UPDATE.address(), JsonObject.of(Constant.COLLECTION, collection().getName(), Constant.ID, id, Constant.DOCUMENT, document));
  }
}
