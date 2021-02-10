package sk.kosickaakademia.deco.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Database {
    private String url;
    private String user;
    private String password;
    private String configFilePath = "resource/config.properties";

    public Database(){
        loadConfig(configFilePath);
    }

    private void loadConfig(String filepath){
        try {
            InputStream inputStream = new FileInputStream(filepath);

            Properties properties=new Properties();

            properties.load(inputStream);

            url=properties.getProperty("url");
            user=properties.getProperty("user");
            password=properties.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
