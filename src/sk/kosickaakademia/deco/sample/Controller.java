package sk.kosickaakademia.deco.sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sk.kosickaakademia.deco.database.Database;
import sk.kosickaakademia.deco.entity.User;

import java.io.IOException;

public class Controller {
    public TextField loginField;
    public Button signInButton;
    public Label failedSignInLabel;
    public PasswordField passwordField;

    public Button getSignInButton() {
        return signInButton;
    }

    public void clickSignIn(MouseEvent mouseEvent) {
        String login = loginField.getText().trim();
        String password = passwordField.getText().trim();

        User user = new Database().login(login, password);

        if (user == null) {
            failedSignInLabel.setVisible(true);
            System.out.println("login unsuccessful");
        }
        else {
            signInButton.getScene().getWindow().hide();
            openMainForm();
            System.out.println("login successful");
            //todo login is not case sensitive
        }
    }

    private void openMainForm(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Chat 2021 App");
            primaryStage.setScene(new Scene(root, 500, 300));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
