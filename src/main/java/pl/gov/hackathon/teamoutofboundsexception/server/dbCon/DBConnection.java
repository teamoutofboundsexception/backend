package pl.gov.hackathon.teamoutofboundsexception.server.dbCon;

import java.sql.*;

public class DBConnection {

    public static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    public static final String DB_URL = "jdbc:mariadb://localhost:3306/application";

    private Connection connection = null;
    String driver, url, username, password;

    public DBConnection(String username, String password){
        this.driver = JDBC_DRIVER;
        this.url = DB_URL;
        this.username = username;
        this.password = password;
    }

    //selects and calls work this way
    public ResultSet executeQuery(String query) throws SQLException {
        initConnection();
        ResultSet rs = runStatement(query);
        return rs;
    }

    private void initConnection() {
        try {
            Class.forName(driver);
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    private ResultSet runStatement(String query) {
        Statement stmt = null;
        ResultSet resultSet = null;
        try {
            stmt = this.connection.createStatement();
            resultSet = stmt.executeQuery(query);
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                this.connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return resultSet;
    }
}
