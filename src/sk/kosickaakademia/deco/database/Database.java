package sk.kosickaakademia.deco.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
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
            ps.setString(2, getMd5(password));

            if (ps.executeUpdate() < 1)
                result = true;

            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String getMd5(String input) {
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            StringBuilder hashtext = new StringBuilder(no.toString(16));
            while (hashtext.length() < 32) {
                hashtext.insert(0, "0");
            }
            return hashtext.toString();
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean login(String login, String password){
        if (login == null || login.isBlank())
            return false;
        if (password == null || password.isBlank())
            return false;

        boolean result = false;
        try {
            Connection connection = getConnection();
            String query = "select * from user where login=? and password=?";

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, login);
            ps.setString(2, getMd5(password));

            ResultSet rs = ps.executeQuery();

            if (rs.next())
                result = true;

            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

}
