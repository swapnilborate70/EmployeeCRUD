package com.test.router;

import com.test.constant.PATH;
import com.test.service.DepartmentService;
import com.test.validation.DepartmentValidation;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class DepartmentRouter extends APIRouter{

  public DepartmentRouter(Router router, Vertx vertx)
  {
    super(router, new DepartmentService(vertx), PATH.DEPARTMENT_PATH.getName(), new DepartmentValidation());
  }

}
