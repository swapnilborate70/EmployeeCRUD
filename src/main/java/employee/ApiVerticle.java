package employee;

import employee.handler.EmployeeHandler;
import employee.repository.EmployeeRepository;
import employee.router.EmployeeRouter;
import employee.service.EmployeeService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class ApiVerticle extends AbstractVerticle {

  private MongoClient mongoClient;

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    Router mainRouter = Router.router(vertx);

    mainRouter.route().handler(BodyHandler.create());

    buildServer(vertx,startPromise,mainRouter);

    vertx.fileSystem().readFile("config.json",dbConfig->{
      if(dbConfig.succeeded())
      {
        System.out.println("Database config success");
        JsonObject configJsonObject = dbConfig.result().toJsonObject();
        JsonObject mongoConfig = configJsonObject.getJsonObject("mongo");
        this.mongoClient = MongoClient.create(vertx, mongoConfig);
        initializeObjects(mainRouter);
      }
      else
      {
        System.out.println("Failed to config database");
        System.out.println(dbConfig.cause().getMessage());
      }
    });
  }
  private void buildServer(Vertx vertx, Promise<Void> promise, Router router)
  {
    final int port = 8888;

    vertx.createHttpServer().requestHandler(router)
      .listen(port, http->{
        if(http.succeeded())
        {
          System.out.println("Http server stared on port : "+port);
          promise.complete();
        } else {
          System.out.println("Http server failed to create !! cause : "+http.cause());
          promise.fail(http.cause());
        }
      });
  }
  public void initializeObjects(Router mainRouter)
  {
    EmployeeRepository employeeRepository = new EmployeeRepository();
    EmployeeService employeeService = new EmployeeService(employeeRepository,mongoClient);
    EmployeeHandler employeeHandler = new EmployeeHandler(employeeService);
    final EmployeeRouter employeeRouter = new EmployeeRouter(vertx,employeeHandler,mainRouter);
    employeeRouter.empRouters();
  }
}
