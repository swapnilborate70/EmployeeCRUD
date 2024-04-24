package com.test.validation;

import com.test.constant.Constant;
import io.netty.util.internal.StringUtil;
import io.vertx.core.json.JsonObject;

public class EmployeeValidation extends Validation{


  @Override
  public boolean validate(JsonObject document) {

    JsonObject failedKeys = new JsonObject();

    //validate Employee
    String name = document.getString(Constant.NAME);
    String deptID = document.getString(Constant.DEPT_ID);
    String empID = document.getString(Constant.EMP_ID);

    if ((name == null || StringUtil.isNullOrEmpty(name))) {
      failedKeys.put(Constant.NAME, name);
    }

    if ((empID == null || StringUtil.isNullOrEmpty(empID))) {
      failedKeys.put(Constant.EMP_ID, empID);
    }

    if ((deptID == null || StringUtil.isNullOrEmpty(deptID))) {
      failedKeys.put(Constant.DEPT_ID, deptID);
    }

    if(failedKeys.isEmpty())
    {
      return true;
    }
    else return false;
  }

  @Override
  public JsonObject countFailedValidations(JsonObject document) {

    JsonObject failedKeys = new JsonObject();

    //validate Employee
    String name = document.getString(Constant.NAME);
    String deptID = document.getString(Constant.DEPT_ID);
    String empID = document.getString(Constant.EMP_ID);

    if ((name == null || StringUtil.isNullOrEmpty(name))) {
      failedKeys.put(Constant.NAME, name);
    }

    if ((empID == null || StringUtil.isNullOrEmpty(empID))) {
      failedKeys.put(Constant.EMP_ID, empID);
    }

    if ((deptID == null || StringUtil.isNullOrEmpty(deptID))) {
      failedKeys.put(Constant.DEPT_ID, deptID);
    }
    return failedKeys;
  }
}
