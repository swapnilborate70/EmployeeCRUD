package com.test.router;

import com.test.constant.Entity;
import com.test.service.DepartmentService;
import com.test.validation.DepartmentValidation;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class DepartmentRouter extends APIRouter{

  public DepartmentRouter(Router router, Vertx vertx)
  {
    super(router, new DepartmentService(vertx), Entity.DEPARTMENT.getPath(), new DepartmentValidation());
  }

}
