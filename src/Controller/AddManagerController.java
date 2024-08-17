package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import Model.Data.Data;
import Model.Person.Manager;
import Model.MainPage.*;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;

public class AddManagerController {
    private Data data;

    @FXML
    TextField nameTextField;

    @FXML
    TextField lastnameTextField;

    @FXML
    TextField usernameTextField;

    @FXML
    TextField passwordTextField;

    @FXML
    Label usernameLabel;

    @FXML
    Label passwordLabel;

    @FXML
    Button makeButton;

    @FXML
    Button backButton;

    @FXML
    ListView<String>channelListView;

    @FXML
    Label channelLabel;

    @FXML
    Label nullLabel;

    @FXML
    Label doneLabel;

    public void initialize(){
        data=LoginPageController.getData();
        passwordLabel.setVisible(false);
        passwordLabel.setVisible(false);
        lastnameTextField.setText(null);
        nameTextField.setText(null);
        usernameTextField.setText(null);
        passwordTextField.setText(null);
        usernameLabel.setVisible(false);
        for (int i = 0; i < data.getChannels().size(); i++) {
            if (data.getChannels().get(i).getManager()==null){
                channelListView.getItems().add(data.getChannels().get(i).getName());
            }
        }
        doneLabel.setVisible(false);
    }


    public void back(MouseEvent mouseEvent) {
        try {
            new PageLoader().load("/View/Page.fxml");
        }catch (Exception e){
            //it never happen
            System.out.println(e.getMessage());
        }
    }


    public void backEnter(MouseEvent mouseEvent) {
        backButton.setUnderline(true);
    }

    public void makeEnter(MouseEvent mouseEvent) {
        makeButton.setUnderline(true);
    }

    public void backExit(MouseEvent mouseEvent) {
        backButton.setUnderline(false);
    }

    public void makeExit(MouseEvent mouseEvent) {
        makeButton.setUnderline(false);
    }


    public void make(MouseEvent mouseEvent) {
        channelLabel.setVisible(false);
        passwordLabel.setVisible(false);
        usernameLabel.setVisible(false);
        nullLabel.setVisible(false);
        doneLabel.setVisible(false);
        if (channelListView.getSelectionModel().getSelectedItem()==null||nameTextField.getText()==null||lastnameTextField.getText()
                ==null||usernameTextField.getText()==null||passwordTextField.getText()==null){
            nullLabel.setText(nullLabel.getText()+" "+"And ListView!");
            nullLabel.setVisible(true);
            return;
        }
        for (int i = 0; i < data.getUsers().size(); i++) {
            if (data.getUsers().get(i).getUsername().equals(usernameTextField.getText())){
                usernameLabel.setVisible(true);
                return;
            }
        }
        for (int i = 0; i < data.getManagers().size(); i++) {
            if (data.getManagers().get(i).getUsername().equals(usernameTextField.getText())){
                usernameLabel.setVisible(true);
                return;
            }
        }
        if (data.getGeneralManager().getUsername().equals(usernameTextField.getText())){
            usernameLabel.setVisible(true);
            return;
        }
        if (passwordTextField.getText().length()<5){
            passwordLabel.setVisible(true);
            return;
        }
        try {
            Manager manager =new Manager(nameTextField.getText(),lastnameTextField.getText(),usernameTextField.getText()
                    ,passwordTextField.getText(),data.getChannelWithName(channelListView.getSelectionModel().getSelectedItem()));
            data.getManagers().add(manager);
        }catch (Exception e){
            if (e.getMessage().equals("NO SUCH CHANNEL!")){
                //it never happen
                System.out.println(e.getMessage());
            }
        }
        try (FileOutputStream write=new FileOutputStream(Paths.get("ProgramData.txt").toFile());
             ObjectOutputStream writeData=new ObjectOutputStream(write)){
            writeData.writeObject(data);
            writeData.flush();
            doneLabel.setVisible(true);
        }catch (Exception e){
            //it never happen
            System.out.println(e.getMessage());
        }
    }
}