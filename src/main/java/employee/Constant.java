package employee;

public class Constant {

  public static final String ID = "_id";
  public static final String COLLECTION = "collection";
  public static final String DOCUMENT = "document";

  public enum Collection {
    EMPLOYEE("Employee"),
    DEPARTMENT("Department");

    private final String name;

    Collection(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }
  }
}
