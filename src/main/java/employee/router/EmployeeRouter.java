package employee.router;

import employee.handler.EmployeeHandler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;

public class EmployeeRouter {

  private final Vertx vertx;
  private final EmployeeHandler employeeHandler;

  private final Router empRouter;

  public EmployeeRouter(Vertx vertx, EmployeeHandler employeeHandler, Router empRouter) {
    this.vertx = vertx;
    this.employeeHandler = employeeHandler;
    this.empRouter = empRouter;

  }

  public void empRouters()
  {
    empRouter.route("/employee").method(HttpMethod.POST).handler(employeeHandler::create);
    empRouter.route("/employee/:id").method(HttpMethod.GET).handler(employeeHandler::get);
    empRouter.route("/employees").method(HttpMethod.GET).handler(employeeHandler::getAll);
    empRouter.route("/employee/:id").method(HttpMethod.DELETE).handler(employeeHandler::delete);
    empRouter.route("/employee/:id").method(HttpMethod.PUT).handler(employeeHandler::update);
  }

}
