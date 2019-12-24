package socketsapp.connetions;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MyConnection {

    public java.sql.Connection connectToDB() {
        FileInputStream fileInputStream;
        Properties properties = new Properties();
        try {
            fileInputStream = new FileInputStream("/Users/amir/Desktop/MyProjects/proj/MyShop/src/main/java/ru/javalab/socketsapp/properties/db.properties");
            properties.load(fileInputStream);
            String user = properties.getProperty("user");
            String password = properties.getProperty("pass");
            String url = properties.getProperty("url");
            Class.forName("org.postgresql.Driver");
            java.sql.Connection connection = DriverManager.getConnection(url, user, password);
            return connection;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return null;
    }
}
