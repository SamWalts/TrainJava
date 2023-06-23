package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TableManager {
    private static TableManager instance;
    private DBUtils dbUtils;

    public TableManager() {
        dbUtils = new DBUtils("Train.db");
    }

    public static TableManager getInstance() {
        if (instance == null) {
            instance = new TableManager();
        }
        return instance;
    }
    // Sends blobmanager a dbUtils instance
    public BlobManager createBlobManager() {
        return new BlobManager(dbUtils);
    }
    // Created the table for switches and stations
    public void createTable(int value, String tableName) {
        String sqlTableName = "table_" + tableName;
        String createTableSQL = "CREATE TABLE IF NOT EXISTS " + sqlTableName + " (column1 INTEGER, column2 TEXT)";

        try {
            dbUtils.connect();
            dbUtils.createNewTable(sqlTableName, createTableSQL);
            if (value > 1) {
                insertions(value, sqlTableName);
            }
            System.out.println("Table created: " + sqlTableName);
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }
    // Will make the table for the picture BLOB table.
    public void createTable(String tableName) {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS " + tableName + " (id INTEGER PRIMARY KEY, file_data BLOB)";

        try {
            dbUtils.connect();
            dbUtils.createNewTable(tableName, createTableSQL);
            System.out.println("Table created: " + tableName);
        } catch (SQLException e) {
            System.out.println("Error creating picture table: " + e.getMessage());
        }
    }
    // For loop to take the integer from generateButtons and make that number of rows.
    public void insertions(int value, String sqlTableName) {
        for (Integer i = 1; i <= value; i++) {
            insertSwitchOrStation(sqlTableName, value);
        }
    }
    // Takes the data from insertions and calls dbUtils to place in sqlitedb.
    public void insertSwitchOrStation(String sqlTableName, int value) {
        String insertIntoSQL = "INSERT INTO " + sqlTableName + "(column1, column2) VALUES(?, ?)";
        try {
            // Generate appropriate row values for insertion
            Integer column1 = value;
            String column2 = "value" + value;

            // Prepare the SQL statement
            PreparedStatement pstmt = dbUtils.connect().prepareStatement(insertIntoSQL);
            pstmt.setInt(1, column1);
            pstmt.setString(2, column2);

            // Execute the SQL statement
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Takes the file data of our image and converts it into binary stream.
    public void insertFileAsBlob(File file, String tableName, String columnName) throws IOException {
        String insertStatement = "INSERT INTO " + tableName + " (" + columnName + ") VALUES (?)";

        try {
            dbUtils.connect();

            // Read file contents into a byte array
            byte[] fileData = readFileData(file);

            // Prepare the SQL statement with a parameter for the BLOB value
            PreparedStatement pstmt = dbUtils.connect().prepareStatement(insertStatement);
            pstmt.setBytes(1, fileData);

            // Execute the SQL statement
            pstmt.executeUpdate();

            System.out.println("File inserted as BLOB successfully.");
        } catch (SQLException e) {
            System.out.println("Error inserting file as BLOB: " + e.getMessage());
        }
    }

    private byte[] readFileData(File file) throws IOException {
        byte[] fileData;
        try (InputStream inputStream = new FileInputStream(file)) {
            fileData = new byte[inputStream.available()];
            inputStream.read(fileData);
        }
        return fileData;
    }
    /*
    private void writeBlobToFile(byte[] fileData, String outputFile) {
        try (OutputStream outputStream = new FileOutputStream(outputFile)) {
            outputStream.write(fileData);
        } catch (IOException e) {
            System.out.println("Error writing BLOB file to disk: " + e.getMessage());
        }
    }*/
}