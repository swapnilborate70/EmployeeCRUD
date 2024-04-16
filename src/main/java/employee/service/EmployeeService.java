package employee.service;

import employee.Constant;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public class EmployeeService implements IService {

  private final Vertx vertx;

  public EmployeeService(Vertx vertx) {
    this.vertx = vertx;
  }

  @Override
  public Constant.Collection collection() {
    return Constant.Collection.EMPLOYEE;
  }

  @Override
  public Vertx vertx() {
    return vertx;
  }

  @Override
  public Future<JsonObject> find(int id) {
    // TODO: To Be Implemented
    return null;
  }


  @Override
  public Future<Void> update(int id, JsonObject document) {
    // TODO: To Be Implemented
    return null;
  }

  @Override
  public Future<Void> delete(int id) {
    // TODO: To Be Implemented
    return null;
  }
}
