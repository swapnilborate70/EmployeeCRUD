package com.test.router;

import com.test.constant.PATH;
import com.test.service.EmployeeService;
import com.test.validation.EmployeeValidation;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class EmployeeRouter extends APIRouter{

  public EmployeeRouter(Router router, Vertx vertx)
  {
    super(router, new EmployeeService(vertx), PATH.EMPLOYEE_PATH.getName(), new EmployeeValidation());
  }

}
