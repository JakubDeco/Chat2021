package sk.kosickaakademia.deco.entity;

public class User {
    private int id;
    private String login;
    private String hashPassword;

    public User(int id, String login, String hashPassword) {
        this.id = id;
        this.login = login;
        this.hashPassword = hashPassword;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getHashPassword() {
        return hashPassword;
    }
}
