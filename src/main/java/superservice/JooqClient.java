package superservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import lombok.Getter;
import org.sqlite.JDBC;

public class JooqClient {

  private static final String dbFilePath = System.getProperty("dbPath");
  private static final String CON_STR =
      "jdbc:sqlite:" + dbFilePath;

  private static JooqClient instance = null;

  public static synchronized JooqClient getInstance() throws SQLException {
    if (instance == null) {
      synchronized (JooqClient.class) {
        if (instance == null) {
          instance = new JooqClient();
        }
      }
    }
    return instance;
  }

  @Getter
  private Connection connection;

  protected JooqClient() throws SQLException {
    DriverManager.registerDriver(new JDBC());
    this.connection = DriverManager.getConnection(CON_STR);
  }





}
