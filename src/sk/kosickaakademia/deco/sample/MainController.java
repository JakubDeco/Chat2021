package sk.kosickaakademia.deco.sample;


import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class MainController {

    public Label loginName;
    public Button refreshBtn;
    public TextArea inboxTextField;
    public Button sendBtn;
    public TextField recipient;
    public TextField newMessage;

    public void setLoginNameText(String name){
        if (name == null || name.isBlank())
            return;

        loginName.setText(name);
    }

    public void sendMessage(MouseEvent mouseEvent) {

    }
}
