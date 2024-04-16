package employee.service;

import employee.Constant;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

import java.util.List;

public class DepartmentService implements IService {

  private final Vertx vertx;

  public DepartmentService(Vertx vertx) {
    this.vertx = vertx;
  }

  @Override
  public Vertx vertx() {
    return null;
  }

  @Override
  public Constant.Collection collection() {
    return Constant.Collection.DEPARTMENT;
  }

  @Override
  public Future<JsonObject> find(int id) {
    return null;
  }


  @Override
  public Future<Void> update(int id, JsonObject document) {
    return null;
  }

  @Override
  public Future<Void> delete(int id) {
    return null;
  }
}
