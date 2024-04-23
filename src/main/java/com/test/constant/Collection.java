package com.test.constant;

public enum Collection {

  EMPLOYEE("Employee"),

  DEPARTMENT("Department");

  private final String name;

  Collection(String name)
  {
    this.name=name;
  }

  public String getName()
  {
    return name;
  }

}
