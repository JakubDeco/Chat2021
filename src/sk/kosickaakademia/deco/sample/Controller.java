package sk.kosickaakademia.deco.sample;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import sk.kosickaakademia.deco.database.Database;
import sk.kosickaakademia.deco.entity.User;

public class Controller {
    public TextField loginField;
    public Button signInButton;
    public Label failedSignInLabel;
    public PasswordField passwordField;

    public void clickSignIn(MouseEvent mouseEvent) {
        String login = loginField.getText().trim();
        String password = passwordField.getText().trim();

        User user = new Database().login(login, password);

        if (user == null) {
            failedSignInLabel.setVisible(true);
            System.out.println("login unsuccessful");
        }
        else {
            failedSignInLabel.setVisible(false);
            System.out.println("login successful");
            //todo login is not case sensitive
        }
    }
}
