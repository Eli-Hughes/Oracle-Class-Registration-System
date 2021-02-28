import java.io.IOException;
import java.sql.*;

// Establishes a connection to the Oracle server
public class Database {
    private Connection connection;
    private Statement statement;


    public Database (String username, String password) throws SQLException, IOException {
        connection = null;
        try {
            //Load the Oracle JDBC driver
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch(ClassNotFoundException e) {
            System.out.println("Could not load the driver");
        }
        try {
            // replace with actual connection url for the database being used
            String url = "url";
            // connect to the oracle server
            connection =  DriverManager.getConnection(url, username, password);
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
        if (connection != null) {
            statement = connection.createStatement();
        } else {
            statement = null;
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public Statement getStatement() {
        return statement;
    }
}
