package com.test.validation;

import com.test.constant.Constant;
import com.test.constant.ConstantPATH;
import com.test.constant.ResponseConstants;
import com.test.response.Response;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class EmployeeValidation extends Validation{

  @Override
  public void validate(RoutingContext routingContext) {

    //validate Employee
    if (routingContext.normalizedPath().equals(ConstantPATH.EMP)) {
      JsonObject document = routingContext.body().asJsonObject();
      String name = document.getString(Constant.NAME);
      String deptID = document.getString(Constant.DEPT_ID);
      String empID = document.getString(Constant.EMP_ID);

      if (!(name == null || name.isBlank() || name.isEmpty())) {
        if (!(deptID == null || deptID.isEmpty() || deptID.isBlank())) {
          if (!(empID == null || empID.isEmpty() || empID.isBlank())) {
            routingContext.next();
          } else Response.response(routingContext, ResponseConstants.FAILURE_CODE, ResponseConstants.VALIDATION_FAILED);
        } else Response.response(routingContext, ResponseConstants.FAILURE_CODE, ResponseConstants.VALIDATION_FAILED);
      } else Response.response(routingContext, ResponseConstants.FAILURE_CODE, ResponseConstants.VALIDATION_FAILED);
    }
  }

}
