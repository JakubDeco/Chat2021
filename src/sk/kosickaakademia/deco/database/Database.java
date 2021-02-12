package sk.kosickaakademia.deco.database;

import sk.kosickaakademia.deco.entity.Message;
import sk.kosickaakademia.deco.entity.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Database {
    private String url;
    private String user;
    private String password;

    private final String changePassword = "update user set password=? where login=? and password=?";
    private final String getUserID = "select id from user where login=?";
    private final String getMyMessages = "select * from message where toUser=?";
    private final String deleteAllMyMessages = "delete from messages where toUser=?";
    private final String sendMessage = "insert into message(fromUser, toUser, text) values(?, ?, ?)";

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

            if (ps.executeUpdate() > 0)
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

    public User login(String login, String password){
        if (login == null || login.isBlank())
            return null;
        if (password == null || password.isBlank())
            return null;

        User result = null;
        try {
            Connection connection = getConnection();
            String query = "select * from user where login=? and password=?";

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, login);
            ps.setString(2, getMd5(password));

            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                int id = rs.getInt("id");
                String hashPassword = rs.getString("password");

                result = new User(id, login, hashPassword);
            }


            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean changePassword(String login, String oldPassword, String newPassword){
        if (login == null || login.isBlank())
            return false;
        if (oldPassword == null || oldPassword.isBlank())
            return false;
        if (newPassword == null || newPassword.isBlank())
            return false;

        boolean result = false;
        try {
            Connection connection = getConnection();
            if (connection == null)
                return false;

            PreparedStatement ps = connection.prepareStatement(changePassword);
            ps.setString(1, newPassword);
            ps.setString(2, login);
            ps.setString(3, oldPassword);

            if (ps.executeUpdate() != 0)
                result = true;

            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public int getUserID(String login){
        int id = -1;
        if (login == null || login.isBlank())
            return id;

        try {
            Connection connection = getConnection();
            if (connection == null)
                return id;

            PreparedStatement ps = connection.prepareStatement(getUserID);
            ps.setString(1, login);

            ResultSet rs = ps.executeQuery();
            if (rs.next())
                id = rs.getInt("id");

            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    public List<Message> getMyMessages(String login){
        List<Message> list = new ArrayList<>();
        if (login == null || login.isBlank())
            return list;

        int userID = getUserID(login);
        if (userID == -1)
            return list;

        try {
            Connection connection = getConnection();
            if (connection == null)
                return list;

            PreparedStatement ps = connection.prepareStatement(getMyMessages);
            ps.setInt(1, userID);

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int id = rs.getInt("id");
                String from = rs.getString("fromUser");
                String to = rs.getString("toUser");
                Date date = rs.getDate("dt");
                String text = rs.getString("text");

                list.add(new Message(id, from, to, date, text));
            }

            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean deleteAllMyMessages(String login){
        boolean result = false;
        if (login == null || login.isBlank())
            return result;

        int userID = getUserID(login);
        if (userID == -1)
            return result;

        try {
            Connection connection = getConnection();
            if (connection == null)
                return result;

            PreparedStatement ps = connection.prepareStatement(deleteAllMyMessages);
            ps.setInt(1, userID);

            if (ps.executeUpdate() != 0)
                result = true;

            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean sendMessage(int from, String toUser, String content){
        if (content == null || content.isBlank())
            return false;

        int to = getUserID(toUser);
        if (to == -1)
            return false;

        try {
            Connection connection = getConnection();
            if (connection == null)
                return false;

            PreparedStatement ps = connection.prepareStatement(sendMessage);
            ps.setInt(1, from);
            ps.setInt(2, to);
            ps.setString(3, content);

            if (ps.executeUpdate() == 0){
                connection.close();
                return false;
            }
            else {
                connection.close();
                return true;
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
