package com.test.validation;

import io.vertx.core.json.JsonObject;

public abstract class Validation {
  public abstract JsonObject validate(JsonObject document);
}
