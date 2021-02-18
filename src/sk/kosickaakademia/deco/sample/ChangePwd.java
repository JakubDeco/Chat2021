package sk.kosickaakademia.deco.sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import sk.kosickaakademia.deco.database.Database;

public class ChangePwd {
    public PasswordField oldPwdField;
    public PasswordField newPwdField;
    public PasswordField confirmPwdField;
    public Button submitBtn;
    public Label submitLbl;
    public Label loginLbl;


    public void changePassword(ActionEvent actionEvent) {
        Database database = new Database();
        String oldP = oldPwdField.getText();
        String newP = newPwdField.getText();
        String confirmP = confirmPwdField.getText();

        if (!newP.equals(confirmP)){
            submitLbl.setVisible(true);
            oldPwdField.setText("");
            newPwdField.setText("");
            confirmPwdField.setText("");
            return;
        }
        if (database.changePassword(loginLbl.getText(), oldP, newP)){
            submitLbl.setVisible(false);
            submitLbl.getScene().getWindow().hide();
        }
        else {
            submitLbl.setVisible(true);
            oldPwdField.setText("");
            newPwdField.setText("");
            confirmPwdField.setText("");
        }
    }
}
