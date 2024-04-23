package com.test.constant;

public enum PATH {

  EMPLOYEE_PATH("/employee"),

  DEPARTMENT_PATH("/dept"),

  PARAM_ID("id");
  private final String name;

  PATH(String name)
  {
    this.name=name;
  }

  public String getName()
  {
    return name;
  }

}
