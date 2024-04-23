package com.test.router;

import com.test.constant.ConstantPATH;
import com.test.service.DepartmentService;
import com.test.validation.Validation;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class DepartmentRouter extends APIRouter{

  public DepartmentRouter(Router router, Vertx vertx, Validation validation)
  {
    super(router, new DepartmentService(vertx), ConstantPATH.DEPT, validation);
  }

}
