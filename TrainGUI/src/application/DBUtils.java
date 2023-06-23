package application;
//TODO: Change the methods to implement best practices such as prepared statements
import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class DBUtils {

    private String fileName;
    private Connection conn;

    public DBUtils(String fileName) {
        this.fileName = fileName;
    }

    public Connection connect() {
    	String filePath = System.getProperty("user.dir");
        String url = "jdbc:sqlite:" + filePath + File.separator + fileName;        
        System.out.println(url);
        // SQLite connection string
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
        }
        return conn;
    }

    public static void createNewDatabase(String fileName) {
        String url = "jdbc:sqlite:" + fileName;

        try (Connection conn = DriverManager.getConnection(url)) {
            // Rest of the code...
        } catch (SQLException e) {
            System.out.println("Error creating or connecting to the database: " + e.getMessage());
        }
    }
    
    public void createNewTable(String tableName, String sql) throws SQLException {
        conn = connect();
        if (conn != null) {
            conn.createStatement().executeUpdate(sql);
            
            System.out.println("Table created: " + tableName);
        } else {
            System.out.println("Not connected to any database. Call connect() method first.");
        }
    }

    public void deleteTable(String tableName) throws SQLException {
        conn = connect();
        if (conn != null) {
            String deleteTableSQL = "DROP TABLE IF EXISTS " + tableName;
            conn.createStatement().executeUpdate(deleteTableSQL);
            System.out.println("Table deleted: " + tableName);
        } else {
            System.out.println("Not connected to any database. Call connect() method first.");
        }
    }

    public void insertData(String tableName, String sql) throws SQLException {
        conn = connect();
        if (conn != null) {
            try (PreparedStatement insertValue = conn.prepareStatement(sql)) {
                insertValue.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Not connected to any database. Call connect() method first.");
        }
    }

    public void closeConnection() throws SQLException {
        if (conn != null) {
            conn.close();
            System.out.println("Database connection closed.");
        }
    }
    public void queryTable(String tableName, String sql) throws SQLException {
    	conn = connect();
    	if (conn != null) {
    }
}
}
