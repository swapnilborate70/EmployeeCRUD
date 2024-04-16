package employee;

public enum AddressBook {
  CREATE,
  UPDATE,
  DELETE,
  FIND,
  FIND_ALL;

  public String address(){
    return name() + "_mongo_db_operation";
  }
}
