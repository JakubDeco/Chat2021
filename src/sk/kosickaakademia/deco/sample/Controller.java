package sk.kosickaakademia.deco.sample;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class Controller {
    public TextField loginField;
    public Button signInButton;
    public Label failedSignInLabel;
    public PasswordField passwordField;

    public void clickSignIn(MouseEvent mouseEvent) {
        System.out.println("huraaa");
    }
}
