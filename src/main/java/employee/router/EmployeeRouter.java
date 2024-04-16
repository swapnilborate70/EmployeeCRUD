package employee.router;

import employee.response.Response;
import employee.service.EmployeeService;
import employee.service.IService;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class EmployeeRouter {

  private final Router empRouter;

  private final IService service;

  public EmployeeRouter(Vertx vertx, Router router) {
    this.empRouter = router;
    this.service = new EmployeeService(vertx);
    init();
  }

  public void init() {

    empRouter.route("/employee").method(HttpMethod.POST).handler(this::create);
//    empRouter.route("/employee/:id").method(HttpMethod.GET).handler(this::get);
    empRouter.route("/employees").method(HttpMethod.GET).handler(this::getAll);
//    empRouter.route("/employee/:id").method(HttpMethod.DELETE).handler(this::delete);
//    empRouter.route("/employee/:id").method(HttpMethod.PUT).handler(this::update);
  }


  public void create(RoutingContext rcx) {
    final JsonObject document = rcx.body().asJsonObject();
    service.create(document)
      .onSuccess(success -> Response.createdResponse(rcx))
      .onFailure(fail -> Response.failResponse(rcx, fail));
  }

//  public void get(RoutingContext rc) {
//    final int id = Integer.parseInt(rc.pathParam("id"));
//    service.getEmployee(id)
//      .onSuccess(success -> Response.getResponse(rc, success))
//      .onFailure(failure -> Response.notFoundResponse(rc, failure.getMessage()));
//  }
//
//  public void delete(RoutingContext rc) {
//    final int id = Integer.parseInt(rc.pathParam("id"));
//    service.deleteEmployee(id)
//      .onSuccess(success -> Response.deleteResponse(rc, success))
//      .onFailure(failure -> Response.notFoundResponse(rc, failure.getMessage()));
//  }
//
//  public void update(RoutingContext rc) {
//    final int id = Integer.parseInt(rc.pathParam("id"));
//    JsonObject jsonObject = rc.body().asJsonObject();
//    service.updateEmployee(id, jsonObject)
//      .onSuccess(success -> Response.updateResponse(rc))
//      .onFailure(failure -> Response.notFoundResponse(rc, failure.getMessage()));
//  }
//
  public void getAll(RoutingContext rcx) {
    service.findAll()
      .onSuccess(result -> {
        rcx.response().setStatusCode(200).end(new JsonArray(result).encodePrettily());
      }).onFailure(failure -> {
        Response.notFoundResponse(rcx, failure.getMessage());
      });
  }
}
