package com.test.response;

import com.test.constant.ResponseConstants;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class Response {

  public static void jsonResponse(RoutingContext rc, int statusCode, JsonObject responseBody) {
    rc.response().setStatusCode(statusCode)
      .putHeader(ResponseConstants.CONTENT_TYPE_HEADER, ResponseConstants.APPLICATION_JSON);

    rc.response().end(Json.encodePrettily(responseBody));
  }
}
