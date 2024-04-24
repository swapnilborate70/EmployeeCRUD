package com.test.validation;

import com.test.constant.Constant;
import io.netty.util.internal.StringUtil;
import io.vertx.core.json.JsonObject;

public class DepartmentValidation extends Validation {

  @Override
  public boolean validate(JsonObject document) {

    JsonObject failedKeys = new JsonObject();

    //validate Department
    String deptID = document.getString(Constant.DEPT_ID);
    String name = document.getString(Constant.DEPT_NAME);

    if ((name == null || StringUtil.isNullOrEmpty(name))) {
      failedKeys.put(Constant.NAME, name);
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
    //validate Department
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
