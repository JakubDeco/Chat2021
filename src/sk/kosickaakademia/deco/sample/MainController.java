package sk.kosickaakademia.deco.sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sk.kosickaakademia.deco.database.Database;
import sk.kosickaakademia.deco.entity.Message;

import java.io.IOException;
import java.util.List;

public class MainController {

    public Label loginName;
    public Button refreshBtn;
    public TextArea inboxTextField;
    public Button sendBtn;
    public TextField recipient;
    public TextField newMessage;
    public Label sendErrorLbl;
    public Label refreshLbl;

    public void setLoginNameText(String name){
        if (name == null || name.isBlank())
            return;

        loginName.setText(name);
    }

    public void sendMessage(ActionEvent mouseEvent) {
        Database database = new Database();

        int from = database.getUserID(loginName.getText());
        String to = recipient.getText();
        String contents = newMessage.getText();

        if (from == -1 || to == null || to.isBlank() || contents == null || contents.isBlank())
            return;

        if (database.sendMessage(from,to,contents)){
            newMessage.setText("");
            recipient.setText("");
            sendErrorLbl.setVisible(false);
        }
        else {
            sendErrorLbl.setVisible(true);
        }
    }

    public void refreshMessages(ActionEvent actionEvent) {
        Database database = new Database();
        List<Message> messages = database.getMyMessages(loginName.getText());
        if (messages == null || messages.isEmpty()){            
            refreshLbl.setVisible(true);
            inboxTextField.setText("");
        }
        else {
            StringBuilder sb = new StringBuilder();
            for (Message temp :
                    messages) {
                sb.append(temp.getDate()).append(" ");
                sb.append(temp.getFrom()).append(":    ");
                sb.append(temp.getContent()).append("\n");
            }
            inboxTextField.setText(sb.toString());
            database.deleteAllMyMessages(loginName.getText());
        }
    }

    public void logout(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Chat 2021 App");
            primaryStage.setScene(new Scene(root, 500, 300));
            primaryStage.show();
            loginName.getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
