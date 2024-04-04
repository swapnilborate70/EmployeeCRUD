package com.employee.dao;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeData {

  List<JsonObject> employeeData = new ArrayList<>();

  public JsonObject save(JsonObject jsonObject)
  {
    employeeData.add(jsonObject);
    return jsonObject;
  }

  public void delete(JsonObject jsonObject)
  {
    employeeData.remove(jsonObject);
  }

  public JsonObject findById(long id)
  {
    JsonObject jsonObjectOfResponse = null;
    for(JsonObject jsonObject : employeeData)
    {
      int idOfObject = jsonObject.getInteger("id");
      if(idOfObject == id)
      {
        jsonObjectOfResponse = jsonObject;
        break;
      }
    }
    return jsonObjectOfResponse;
  }

  public List<JsonObject> findAll()
  {
    return employeeData;
  }
}
