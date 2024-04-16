package employee.service;

import employee.repository.EmployeeRepository;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

import java.util.List;

public class EmployeeService
{
  EmployeeRepository employeeRepository;

  private final String collectionName = "empcollection";

  private final MongoClient mongoClient;

  public EmployeeService(EmployeeRepository employeeRepository, MongoClient mongoClient) {
    this.employeeRepository = employeeRepository;
    this.mongoClient = mongoClient;
  }

  public Future<String> addEmployee(JsonObject jsonObject)
  {
    return employeeRepository.create(jsonObject,mongoClient,collectionName);
  }

  public Future<JsonObject> getEmployee(int id)
  {
    return employeeRepository.find(id,mongoClient,collectionName)
      .compose(result->{
        if(result != null)
        {
          return Future.succeededFuture(result);
        } else return Future.failedFuture("Record not found");
      });
  }

  public Future<List<JsonObject>> getAllEmployees()
  {
    return employeeRepository.findAll(mongoClient,collectionName);
  }

  public Future<String> deleteEmployee(int id) {
    return employeeRepository.delete(id,mongoClient,collectionName)
      .compose(result -> {
        if (result != null) {
          return Future.succeededFuture("Record deleted");
        } else {
          return Future.failedFuture("Record not found");
        }
      });
  }

  public Future<String> updateEmployee(int id, JsonObject updatedJsonObject) {
    return employeeRepository.update(updatedJsonObject, id,mongoClient,collectionName)
      .compose(result -> {
        if (result != null) {
          return Future.succeededFuture();
        } else {
          return Future.failedFuture("Update failed. Record not found with ID : "+id+" ");
        }
      });
  }
}
