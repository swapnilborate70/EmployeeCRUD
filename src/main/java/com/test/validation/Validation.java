package com.test.validation;

import io.vertx.core.json.JsonObject;

public abstract class Validation {
  public abstract boolean validate(JsonObject document);

  public abstract JsonObject countFailedValidations(JsonObject document);
}
