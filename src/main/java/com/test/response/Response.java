package com.test.response;

import com.test.constant.ResponseConstants;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class Response {

  public static <T> void jsonResponse(RoutingContext rc, int statusCode, T payload) {
    rc.response().setStatusCode(statusCode)
      .putHeader(ResponseConstants.CONTENT_TYPE_HEADER, ResponseConstants.APPLICATION_JSON);

    if (payload instanceof JsonObject) {
      rc.response().end(Json.encodePrettily((JsonObject) payload));
    } else if (payload instanceof JsonArray) {
      rc.response().end(Json.encodePrettily((JsonArray) payload));
    } else if (payload instanceof String) {
      rc.response().end(Json.encodePrettily(JsonObject.of("status", payload)));
    } else {
      // if unsupported types
      rc.response().setStatusCode(500)
        .end(Json.encodePrettily( JsonObject.of("error", "Unsupported payload type")));
    }
  }

}
