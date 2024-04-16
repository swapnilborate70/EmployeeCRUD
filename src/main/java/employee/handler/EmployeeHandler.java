package employee.handler;

import employee.response.Response;
import employee.service.EmployeeService;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class EmployeeHandler {

  final EmployeeService employeeService;

  public EmployeeHandler(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  public void create(RoutingContext rcx) {
    final JsonObject jsonObject = rcx.body().asJsonObject();
    employeeService.addEmployee(jsonObject)
      .onSuccess(success -> Response.createdResponse(rcx))
      .onFailure(fail -> Response.failResponse(rcx, fail));
  }

  public void get(RoutingContext rc)
  {
    final int id = Integer.parseInt(rc.pathParam("id"));
    employeeService.getEmployee(id)
      .onSuccess(success-> Response.getResponse(rc,success))
      .onFailure(failure-> Response.notFoundResponse(rc, failure.getMessage()));
  }

  public void delete(RoutingContext rc)
  {
    final int id = Integer.parseInt(rc.pathParam("id"));
    employeeService.deleteEmployee(id)
      .onSuccess(success-> Response.deleteResponse(rc, success))
      .onFailure(failure-> Response.notFoundResponse(rc, failure.getMessage()));
  }

  public void update(RoutingContext rc)
  {
    final int id = Integer.parseInt(rc.pathParam("id"));
    JsonObject jsonObject = rc.body().asJsonObject();
    employeeService.updateEmployee(id,jsonObject)
      .onSuccess(success-> Response.updateResponse(rc))
      .onFailure(failure-> Response.notFoundResponse(rc, failure.getMessage()));
  }

  public void getAll(RoutingContext rc)
  {
    JsonArray jsonArray = new JsonArray();
    employeeService.getAllEmployees()
      .onSuccess(success->{
        for(JsonObject jsonObject : success)
        {
          jsonArray.add(jsonObject);
        }
        Response.getResponse(rc, jsonArray);
      }).onFailure(failure->{
        Response.notFoundResponse(rc,failure.getMessage());
      });
  }

}
