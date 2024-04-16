package employee;

import employee.repository.DBOperations;
import io.vertx.core.Vertx;

public class Bootstrap {

  public static void main(String[] args) {

    Vertx vertx = Vertx.vertx();

    vertx.deployVerticle(new APIVerticle());
    vertx.deployVerticle(new DBOperations());
  }

}
