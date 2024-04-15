package employee;

import io.vertx.core.Vertx;

public class Main {

  public static void main(String[] args) {
    Vertx vertx1 = Vertx.vertx();
    vertx1.deployVerticle(new ApiVerticle());
  }

}
