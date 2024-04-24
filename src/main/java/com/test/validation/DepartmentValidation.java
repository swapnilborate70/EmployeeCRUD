package com.test.validation;

import com.test.constant.Constant;
import io.netty.util.internal.StringUtil;
import io.vertx.core.json.JsonObject;

public class DepartmentValidation extends Validation {


  @Override
  public JsonObject checkInvalidPairs(JsonObject document) {

    JsonObject invalidPairs = new JsonObject();
    //validate Department
    String deptID = document.getString(Constant.DEPT_ID);
    String name = document.getString(Constant.DEPT_NAME);

    if ((name == null || StringUtil.isNullOrEmpty(name))) {
      invalidPairs.put(Constant.NAME, name);
    }

    if ((deptID == null || StringUtil.isNullOrEmpty(deptID))) {
      invalidPairs.put(Constant.DEPT_ID, deptID);
    }
    return invalidPairs;
  }
}
