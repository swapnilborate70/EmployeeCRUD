package com.employee.service;
import com.employee.dao.EmployeeData;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
public class EmployeeService {

  EmployeeData employeeData = new EmployeeData();


  public void addEmployee(RoutingContext routingContext)
  {
    JsonObject jsonObject = routingContext.getBodyAsJson();
    int id = jsonObject.getInteger("id");
    JsonObject oldJsonObject = employeeData.findById(id);
    if(id !=0 && oldJsonObject ==null)
    {
      employeeData.save(jsonObject);
      routingContext.response().putHeader("content-type", "application/json");
      routingContext.response().end(jsonObject.encode());
    }
    else {
      routingContext.response().putHeader("content-type", "application/json");
      routingContext.response().end(new JsonObject().put("status","Record is already available with provided id in body").encode());
    }

  }

  public void getEmployee(RoutingContext routingContext)
  {
    int id =Integer.parseInt(routingContext.pathParam("id"));
    JsonObject employeeJsonObject = employeeData.findById(id);

    if(employeeJsonObject != null)
    {
      routingContext.response().putHeader("content-type", "application/json");
      routingContext.response().end(employeeJsonObject.encode());
    }
    else {
      routingContext.response().putHeader("content-type", "application/json");
      routingContext.response().setStatusCode(404).end(new JsonObject().put("status","Employee not found").encode());
    }
  }


  public void getAllEmployee(RoutingContext routingContext)
  {
    JsonArray jsonArrayOfEmployees = JsonArray.of(employeeData.findAll());
    routingContext.response().putHeader("content-type", "application/json");
    routingContext.response().end(jsonArrayOfEmployees.encode());
  }

  public void deleteEmployee(RoutingContext routingContext)
  {
    int id =Integer.parseInt(routingContext.pathParam("id"));
    JsonObject jsonObject = employeeData.findById(id);
    if(jsonObject != null)
    {
      employeeData.delete(jsonObject);
      routingContext.response().putHeader("content-type", "application/json");
      routingContext.response().end(new JsonObject().put("status","Employee record deleted").encode());
    }
    else {
      routingContext.response().putHeader("content-type", "application/json");
      JsonObject reply = new JsonObject();
      routingContext.response().setStatusCode(404).end(new JsonObject().put("status","Employee record not found").encode());
    }
  }

  public void updateEmployee(RoutingContext routingContext)
  {
    JsonObject newEmployeeJson = routingContext.getBodyAsJson();
    int id =Integer.parseInt(routingContext.pathParam("id"));

    JsonObject employeeJsonObject = employeeData.findById(id);
    if(employeeJsonObject != null)
    {
      employeeJsonObject = newEmployeeJson;
      employeeData.save(employeeJsonObject);

      routingContext.response().putHeader("content-type", "application/json");
      routingContext.response().end(employeeJsonObject.encode());
    }
    else {
      routingContext.response().putHeader("content-type", "application/json");
      routingContext.response().setStatusCode(404).end("Employee details not available");
    }
}}
