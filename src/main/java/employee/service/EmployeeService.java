package employee.service;

import employee.repository.EmployeeRepository;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

import java.util.List;

public class EmployeeService
{

  private final EmployeeRepository employeeRepository;

  public EmployeeService(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  public Future<String> createEmployee(JsonObject jsonObject)
  {
    return employeeRepository.create(jsonObject);
  }

  public Future<JsonObject> getEmployee(int id) {
    return employeeRepository.find(id)
      .compose(result -> {
        if (result != null) {
          return Future.succeededFuture(result);
        } else {
          return Future.failedFuture("Record not found");
        }
      });
  }

  public Future<List<JsonObject>> getAllEmployees()
  {
    return employeeRepository.findAll();
  }

  public Future<String> deleteEmployee(int id) {
    return employeeRepository.delete(id)
      .compose(result -> {
        if (result != null) {
          return Future.succeededFuture("Record deleted");
        } else {
          return Future.failedFuture("Record not found");
        }
      });
  }

  public Future<String> updateEmployee(int id, JsonObject updatedJsonObject) {
    return employeeRepository.update(updatedJsonObject, id)
      .compose(result -> {
        if (result != null) {
          return Future.succeededFuture();
        } else {
          return Future.failedFuture("Update failed. Record not found with ID : "+id+" ");
        }
      });
  }
}
