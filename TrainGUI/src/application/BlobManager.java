package application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BlobManager {
    private DBUtils dbUtils;

    public BlobManager(DBUtils dbUtils) {
        this.dbUtils = dbUtils;
    }

    public byte[] retrieveBlob(String tableName, String columnName, int id) {
        byte[] imageData = null;
        try {
            String query = "SELECT " + columnName + " FROM " + tableName + " WHERE id = ?";
            Connection connection = dbUtils.connect();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                File file = new File("temp.blob");
                FileOutputStream fos = new FileOutputStream(file);
                InputStream input = resultSet.getBinaryStream(columnName);
                byte[] buffer = new byte[1024];
                while (input.read(buffer) > 0) {
                    fos.write(buffer);
                }
                fos.close();
                imageData = Files.readAllBytes(file.toPath());
                file.delete();
            }

            resultSet.close();
            statement.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return imageData;
    }
    
    public void storeBlob(String tableName, String columnName, File file) {
        try {
            String query = "INSERT INTO " + tableName + " (" + columnName + ") VALUES (?)";
            Connection connection = dbUtils.connect();
            PreparedStatement statement = connection.prepareStatement(query);
            byte[] bytes = Files.readAllBytes(file.toPath());

            statement.setBytes(1, bytes);

            statement.executeUpdate();
            
            statement.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    }
