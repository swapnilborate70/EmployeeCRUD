package com.test.validation;

import com.test.constant.Constant;
import io.netty.util.internal.StringUtil;
import io.vertx.core.json.JsonObject;

public class EmployeeValidation extends Validation{
  @Override
  public JsonObject checkInvalidPairs(JsonObject document) {

    JsonObject invalidPairs = new JsonObject();

    //validate Employee
    String name = document.getString(Constant.NAME);
    String deptID = document.getString(Constant.DEPT_ID);
    String empID = document.getString(Constant.EMP_ID);

    if ((name == null || StringUtil.isNullOrEmpty(name))) {
      invalidPairs.put(Constant.NAME, name);
    }

    if ((empID == null || StringUtil.isNullOrEmpty(empID))) {
      invalidPairs.put(Constant.EMP_ID, empID);
    }

    if ((deptID == null || StringUtil.isNullOrEmpty(deptID))) {
      invalidPairs.put(Constant.DEPT_ID, deptID);
    }
    return invalidPairs;
  }
}
