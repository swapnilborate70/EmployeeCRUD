package com.test.validation;

import com.test.constant.Constant;
import io.netty.util.internal.StringUtil;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class DepartmentValidation extends Validation {
  @Override
  public JsonObject validate(RoutingContext routingContext) {

    JsonObject failedKeys = new JsonObject();

    //validate Department
    JsonObject document = routingContext.body().asJsonObject();

    String deptID = document.getString(Constant.DEPT_ID);
    String name = document.getString(Constant.DEPT_NAME);

    if ((name == null || StringUtil.isNullOrEmpty(name))) {
      failedKeys.put(Constant.NAME, name);
    }

    if ((deptID == null || StringUtil.isNullOrEmpty(deptID))) {
      failedKeys.put(Constant.DEPT_ID, deptID);
    }

    return failedKeys;
  }
}
