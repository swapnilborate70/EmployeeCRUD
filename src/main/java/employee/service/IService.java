package employee.service;

import employee.AddressBook;
import employee.Constant;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

import java.util.List;

public interface IService {

  Vertx vertx();

  Constant.Collection collection();

  default Future<Void> create(JsonObject document) {
    return vertx().eventBus().request(AddressBook.CREATE.address(), JsonObject.of(Constant.COLLECTION, collection().getName(), Constant.DOCUMENT, document))
      .mapEmpty();
  }

  Future<JsonObject> find(int id);

  default Future<List<JsonObject>> findAll() {
    return vertx().eventBus().<List<JsonObject>>request(AddressBook.FIND_ALL.address(), collection().getName()).map(Message::body);
  }

  Future<Void> update(int id, JsonObject document);

  Future<Void> delete(int id);
}
