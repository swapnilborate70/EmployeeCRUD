package com.test.validation;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public abstract class Validation {

  public abstract JsonObject validate(RoutingContext routingContext);

}
