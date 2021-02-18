package sk.kosickaakademia.deco.sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import sk.kosickaakademia.deco.database.Database;

public class ChangePsswd {
    public PasswordField oldPasswordField;
    public PasswordField newPasswordField;
    public PasswordField confirmPasswordField;
    public Button submitBtn;
    public Label submitLbl;
    public Label loginLbl;


    public void changePassword(ActionEvent actionEvent) {
        Database database = new Database();
        String oldP = oldPasswordField.getText();
        String newP = newPasswordField.getText();
        String confirmP = confirmPasswordField.getText();

        if (!newP.equals(confirmP)){
            submitLbl.setVisible(true);
            return;
        }
        if (database.changePassword(loginLbl.getText(), oldP, newP)){
            submitLbl.setVisible(false);
        }
        else submitLbl.setVisible(true);
    }
}
