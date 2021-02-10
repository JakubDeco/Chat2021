package sk.kosickaakademia.deco.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    private String url;
    private String user;
    private String password;

    public Database(){
        loadConfig();
    }

    private void loadConfig(){
        try {
            InputStream inputStream = new FileInputStream("resource/config.properties");

            Properties properties=new Properties();

            properties.load(inputStream);

            url=properties.getProperty("url");
            user=properties.getProperty("user");
            password=properties.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url,user,password);
    }

    public boolean insertNewUser(String login, String password){
        if (login == null || login.isBlank())
            return false;
        if (password == null || password.isBlank())
            return false;

        boolean result = false;

        try {
            Connection connection = getConnection();
            String query = "insert into user(login, password) values(?, ?)";

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, login);
            ps.setString(2, password);

            if (ps.executeUpdate() < 1)
                result = true;

            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

}
