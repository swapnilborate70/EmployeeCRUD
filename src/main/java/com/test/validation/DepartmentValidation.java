package com.test.validation;

import com.test.constant.Constant;
import com.test.constant.ConstantPATH;
import com.test.constant.ResponseConstants;
import com.test.response.Response;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class DepartmentValidation extends Validation {
  @Override
  public void validate(RoutingContext routingContext) {

    //validate Department
    if (routingContext.normalizedPath().equals(ConstantPATH.DEPT)) {
      JsonObject document = routingContext.body().asJsonObject();

      String deptID = document.getString(Constant.DEPT_ID);
      String name = document.getString(Constant.DEPT_NAME);


      if(!(name==null || name.isBlank() || name.isEmpty()))
      {
        if(!(deptID==null || deptID.isEmpty() || deptID.isBlank()))
        {
          routingContext.next();
        } else Response.response(routingContext,ResponseConstants.FAILURE_CODE,ResponseConstants.VALIDATION_FAILED);
      } else Response.response(routingContext,ResponseConstants.FAILURE_CODE,ResponseConstants.VALIDATION_FAILED);
    }
  }
}
