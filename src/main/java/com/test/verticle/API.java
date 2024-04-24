package com.test.verticle;

import com.test.router.DepartmentRouter;
import com.test.router.EmployeeRouter;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class API extends AbstractVerticle {

  private static final Logger logger = LoggerFactory.getLogger(API.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    Router mainRouter = Router.router(vertx);
    mainRouter.route().handler(BodyHandler.create());


    new EmployeeRouter(mainRouter, vertx);
    new DepartmentRouter(mainRouter, vertx);

    final int port = 8888;

    vertx.createHttpServer().requestHandler(mainRouter)
      .listen(port)
      .onSuccess(http -> {
        System.out.println("Http server started on port : " + port);
        logger.info("Http server started on port : " + port);

        startPromise.complete();
      }).onFailure(fail -> {
        System.out.println("Http server failed to start !!");
        logger.info("Http server failed to start !!");

        startPromise.complete();
      });
  }
}
