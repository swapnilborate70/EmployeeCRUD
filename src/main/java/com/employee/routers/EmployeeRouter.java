package com.employee.routers;

import com.employee.service.EmployeeService;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import javax.swing.*;

public class EmployeeRouter {

  EmployeeService employeeService = new EmployeeService();
  private final Router router;

  public EmployeeRouter(Vertx vertx) {
    router = Router.router(vertx);
    router.route("/employee/:id").method(HttpMethod.GET).handler(employeeService::getEmployee);
    router.route("/employee").method(HttpMethod.POST).handler(employeeService::addEmployee);
    router.route("/employees").method(HttpMethod.GET).handler(employeeService::getAllEmployee);
    router.route("/employee/:id").method(HttpMethod.PUT).handler(employeeService::updateEmployee);
    router.route("/employee/:id").method(HttpMethod.DELETE).handler(employeeService::deleteEmployee);


  }


  public Router getRouter()
  {
    return router;
  }
}
