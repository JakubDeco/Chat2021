package sk.kosickaakademia.deco.sample;


import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sk.kosickaakademia.deco.database.Database;
import sk.kosickaakademia.deco.entity.Message;

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
            for (Message temp :
                    messages) {
                
            }
        }
            
    }
}
