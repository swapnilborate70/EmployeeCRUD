package com.test.router;

import com.test.constant.ConstantPATH;
import com.test.service.EmployeeService;
import com.test.validation.Validation;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class EmployeeRoute extends APIRouter{

  public EmployeeRoute(Router router, Vertx vertx, Validation validation)
  {

    super(router, new EmployeeService(vertx), ConstantPATH.EMP, validation);

  }

}
