package com.test.validation;

import com.test.constant.Constant;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class EmployeeValidation extends Validation{

  @Override
  public JsonObject validate(RoutingContext routingContext) {

    JsonObject failedKeys = new JsonObject();

    //validate Employee
    JsonObject document = routingContext.body().asJsonObject();
    String name = document.getString(Constant.NAME);
    String deptID = document.getString(Constant.DEPT_ID);
    String empID = document.getString(Constant.EMP_ID);

    if ((name == null || name.isBlank() || name.isEmpty())) {
      failedKeys.put(Constant.NAME, name);
    }

    if ((empID == null || empID.isEmpty() || empID.isBlank())) {
      failedKeys.put(Constant.EMP_ID, empID);
    }

    if ((deptID == null || deptID.isEmpty() || deptID.isBlank())) {
      failedKeys.put(Constant.DEPT_ID, deptID);
    }
    return failedKeys;
  }
}
