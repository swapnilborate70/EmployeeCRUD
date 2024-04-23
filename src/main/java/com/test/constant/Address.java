package com.test.constant;

public enum Address {

  CREATE,
  UPDATE,
  DELETE,
  FIND,
  FIND_ALL;

  public String address()
  {
    return name()+"_db";
  }
}
