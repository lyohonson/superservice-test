package superservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import lombok.Getter;
import org.sqlite.JDBC;

public class JooqClient {

  private static final String DB_FILE_PATH = System.getProperty("dbPath");
  private static final String CON_STR =
      "jdbc:sqlite:" + DB_FILE_PATH;

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

  private JooqClient() throws SQLException {
    DriverManager.registerDriver(new JDBC());
    this.connection = DriverManager.getConnection(CON_STR);
  }





}
