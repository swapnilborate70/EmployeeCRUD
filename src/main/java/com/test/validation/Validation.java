package com.test.validation;

import io.vertx.ext.web.RoutingContext;

public abstract class Validation {

  public abstract void validate(RoutingContext routingContext);

}
