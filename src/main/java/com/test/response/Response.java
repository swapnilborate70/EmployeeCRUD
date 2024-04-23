package com.test.response;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class Response {
  private static final String CONTENT_TYPE_HEADER = "Content-Type";
  private static final String APPLICATION_JSON = "application/json";

  public static void response(RoutingContext rc,int statusCode,String message)
  {
    rc.response().setStatusCode(statusCode)
      .putHeader(CONTENT_TYPE_HEADER, APPLICATION_JSON)
      .end(Json.encodePrettily(new JsonObject().put("status",message)));
  }

  public static void jsonResponse(RoutingContext rc,int statusCode, JsonObject document)
  {
    rc.response().setStatusCode(statusCode)
      .putHeader(CONTENT_TYPE_HEADER, APPLICATION_JSON)
      .end(Json.encodePrettily(document));
  }

  public static void jsonArrayResponse(RoutingContext rc,int statusCode, JsonArray documents)
  {
    rc.response().setStatusCode(statusCode)
      .putHeader(CONTENT_TYPE_HEADER, APPLICATION_JSON)
      .end(Json.encodePrettily(documents));
  }
}
