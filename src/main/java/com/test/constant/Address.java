package com.test.constant;

public enum Address {

  CREATE,
  UPDATE,
  DELETE,
  FIND,
  FIND_ALL,

  VALIDATE_UNIQUE;

  public String address()
  {
    return name()+"_db";
  }
}
