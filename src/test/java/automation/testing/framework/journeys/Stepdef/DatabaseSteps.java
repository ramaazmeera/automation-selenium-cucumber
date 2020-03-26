package automation.testing.framework.journeys.Stepdef;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;

import java.sql.*;

public class DatabaseSteps {

  private Connection conn = null;
  private final String url = "jdbc:postgresql://localhost/3000";
  private final String user = "postgres";
  private final String password = "<add your password>";
  private PreparedStatement stmt = null;
  private ResultSet rset = null;

  private Object object = null;

//  private MongoClient mongoClient;
//  private MongoDatabase db;
//  private Document record;
//  private String collectionContext = null;

  @Given("I connect to database")
  public void iConnectToDatabase() {
    dbConnect();
  }

  public Connection dbConnect() {

    try {

      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
      System.out.println("No JDBC Driver detected");
      e.printStackTrace();
      return conn;
    }

    try {
      conn = DriverManager.getConnection(url, user, password);

      System.out.println("Connected to the PostgreSQL server successfully.");

    } catch (SQLException e) {
      System.out.println("Database connection unsuccessful");
      e.printStackTrace();
      return conn;
    }

    if (conn != null) {
      System.out.println("Database connection successful");
    } else {
      System.out.println("Database connection unsuccessful");
    }

    return conn;
  }

  public String queryTrancheDatabase() throws SQLException {

    try {

      conn = dbConnect();
      stmt =
          conn.prepareStatement(
              "SELECT id FROM tablename where date_to_information_provider Between Current_date-30 and Current_date limit 1;");
      rset = stmt.executeQuery();
      ResultSetMetaData metaData = rset.getMetaData();
      int columnCount = metaData.getColumnCount();

      while (rset.next()) {
        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
          object = rset.getString(columnIndex);
          System.out.printf("%s, ", object == null ? "NULL" : object.toString());
        }
        System.out.printf("%n");
        System.out.println("this is suppose to be outcome= " + object.toString());
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    return object.toString();
  }

  public void updateQuery() throws SQLException {
    Statement stmt = null;
    try {
      conn = dbConnect();
      String sql = "UPDATE COMPANY set SALARY = 25000.00 where ID=1;";
        stmt = conn.createStatement();
         stmt.executeUpdate(sql);
      conn.commit();
      ResultSet rs = stmt.executeQuery( "SELECT * FROM COMPANY;" );
      while ( rs.next() ) {
        int id = rs.getInt("id");
        String  name = rs.getString("name");
        int age  = rs.getInt("age");
        String  address = rs.getString("address");
        float salary = rs.getFloat("salary");
        System.out.println( "ID = " + id );
        System.out.println( "NAME = " + name );
        System.out.println( "AGE = " + age );
        System.out.println( "ADDRESS = " + address );
        System.out.println( "SALARY = " + salary );
        System.out.println();
      }
      rs.close();
      stmt.close();
      conn.close();

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  @And("I connect to mongodatabase")
  public void iConnectToMongodatabase() {
  }
}
