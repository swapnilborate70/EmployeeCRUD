package employee;

import employee.router.EmployeeRouter;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class APIVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    Router mainRouter = Router.router(vertx);

    mainRouter.route().handler(BodyHandler.create());

    new EmployeeRouter(vertx, mainRouter);

    final int port = 8888;

    vertx.createHttpServer().requestHandler(mainRouter)
      .listen(port)
      .onSuccess(x -> {
        System.out.println("Http server stared on port : " + port);
        startPromise.complete();
      })
      .onFailure(error -> {
        System.out.println("Http server failed to create !! cause : " + error.getMessage());
        error.printStackTrace();
        startPromise.fail(error);
      });
  }
}
