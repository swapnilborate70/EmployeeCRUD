package employee.response;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.NoSuchElementException;

public class Response {

  private static final String CONTENT_TYPE_HEADER = "Content-Type";
  private static final String APPLICATION_JSON = "application/json";
  public static void createdResponse(RoutingContext rcx, Object response)
  {
    rcx.response().setStatusCode(201)
      .putHeader(CONTENT_TYPE_HEADER, APPLICATION_JSON)
      .end(Json.encodePrettily(new JsonObject().put("status","Success")));
  }

  public static void updateResponse(RoutingContext rc, Object response)
  {
    rc.response().setStatusCode(201)
      .putHeader(CONTENT_TYPE_HEADER, APPLICATION_JSON)
      .end(Json.encodePrettily(new JsonObject().put("status","Document updated")));
  }

  public static void getResponse(RoutingContext rc, Object response)
  {
    rc.response().setStatusCode(200)
      .putHeader(CONTENT_TYPE_HEADER, APPLICATION_JSON)
      .end(Json.encodePrettily(response));
  }

  public static void notFoundResponse(RoutingContext rc, Object response)
  {
    rc.response().setStatusCode(404)
      .putHeader(CONTENT_TYPE_HEADER, APPLICATION_JSON)
      .end(Json.encodePrettily(new JsonObject().put("status",response)));
  }
  public static void deleteResponse(RoutingContext rc, Object response)
  {
    rc.response().setStatusCode(200)
      .putHeader(CONTENT_TYPE_HEADER, APPLICATION_JSON)
      .end(Json.encodePrettily(new JsonObject().put("status",response)));
  }
  public static void failResponse(RoutingContext rc, Throwable throwable)
  {
    final int status;
    final String msg;

    if (throwable instanceof IllegalArgumentException || throwable instanceof IllegalStateException || throwable instanceof NullPointerException) {
      // Bad Request
      status = 400;
      msg = throwable.getMessage();
    } else if (throwable instanceof NoSuchElementException) {
      // Not Found
      status = 404;
      msg = throwable.getMessage();
    } else {
      // Internal Server Error
      status = 500;
      msg = "Internal Server Error";
    }
    rc.response()
      .setStatusCode(status)
      .putHeader(CONTENT_TYPE_HEADER, APPLICATION_JSON)
      .end(new JsonObject().put("error", msg).encodePrettily());

  }

}
