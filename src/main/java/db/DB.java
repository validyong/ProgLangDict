package main.java.db;

import java.sql.*;

public class DB {

    // JDBC driver name and database URL
    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";

    // Database credentials
    private static final String USER_NAME = "education";
    private static final String PASSWORD = "system123";

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        Connection conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);

        System.out.println("~~~~~~Connected to DB~~~~~~");

        return conn;
    }

    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("______________________");
                System.out.println("|Disconnected from DB|");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(PreparedStatement preparedStatement) {
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
