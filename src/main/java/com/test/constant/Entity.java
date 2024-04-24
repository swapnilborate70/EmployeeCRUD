package com.test.constant;

public enum Entity {

  EMPLOYEE("Employee","/employee"),

  DEPARTMENT("Department","/dept");

  private final String name;

  private final String path;

  Entity(String name, String path)
  {
    this.name=name;
    this.path=path;
  }

  public String getName()
  {
    return name;
  }

  public String getPath()
  {
    return path;
  }

}
