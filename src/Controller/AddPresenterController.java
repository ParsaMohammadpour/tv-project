package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import Model.Channel.Channel;
import Model.Data.Data;
import Model.Person.Presenter;
import Model.MainPage.*;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;

public class AddPresenterController {
    private Data data;

    @FXML
    ChoiceBox<String>make_SetChoiceBox;

    @FXML
    Button setItemButton;

    @FXML
    ListView<String>channelListView;

    @FXML
    ListView<String >programListView;

    @FXML
    TextField nameTextField;

    @FXML
    TextField lastnameTextField;

    @FXML
    TextField nicknameTextField;

    @FXML
    Button doneButton;

    @FXML
    Label nameLabel;

    @FXML
    Button setProgramButton;

    @FXML
    Button backButton;

    @FXML
    Label nullLabel;

    public void initialize(){
        data=LoginPageController.getData();
        setVisibilityTextField(false);
        setVisibilityListView(false);doneButton.setVisible(false);
        make_SetChoiceBox.getItems().add("Make Presenter");
        make_SetChoiceBox.getItems().add("Set For Program");
        for (int i = 0; i < data.getChannels().size(); i++) {
            channelListView.getItems().add(data.getChannels().get(i).getName());
        }
    }

    private void setVisibilityTextField(boolean visibility){
        nameTextField.setVisible(visibility);
        lastnameTextField.setVisible(visibility);
        nicknameTextField.setVisible(visibility);
        nameTextField.setText(null);
        lastnameTextField.setText(null);
        nicknameTextField.setText(null);
    }

    private void setVisibilityListView(boolean visibility){
        channelListView.setVisible(visibility);
        programListView.setVisible(visibility);
        setProgramButton.setVisible(visibility);
    }


    public void setItems(MouseEvent mouseEvent) {
        if (make_SetChoiceBox.getValue()==null)return;
        setVisibilityTextField(false);
        setVisibilityListView(false);
        if (make_SetChoiceBox.getValue().equals("Make Presenter")){
            setVisibilityTextField(true);
        } else {
            setVisibilityListView(true);
        }
        doneButton.setVisible(true);
    }


    public void save(MouseEvent mouseEvent) {
        nameLabel.setVisible(false);
        nullLabel.setVisible(false);
        if (nameTextField.getText()==null||lastnameTextField.getText()==null||nicknameTextField.getText()==null){
            nullLabel.setVisible(true);
            return;
        }
        if (nicknameTextField.isVisible()){
            try {
                Presenter presenter=data.getPresenterWithName(nameTextField.getText());
                nameLabel.setVisible(true);
                return;
            }catch (Exception e){
                if (e.getMessage().equals("NO SUCH PRESENTER")){
                    Presenter presenter=new Presenter(nameTextField.getText(),lastnameTextField.getText(),
                            null,null,nicknameTextField.getText());
                    try {
                        data.addPresenter(presenter);
                        System.out.println("$^#&$#&^$#*U^#^#$$");
                    }catch (Exception exception){
                        //it never happen
                        System.out.println(exception.getMessage());
                    }
                }
            }
        }else {
            try {
                Presenter presenter=data.getPresenterWithName(nameTextField.getText());
                nameLabel.setVisible(true);
                return;
            }catch (Exception e){
                if (e.getMessage().equals("NO SUCH PRESENTER")){
                    Presenter presenter=new Presenter(nameTextField.getText(),lastnameTextField.getText(),
                            null,null,null);
                    try {
                        data.addPresenter(presenter);
                        System.out.println("$^#&$#&^$#*U^#^#$$");
                    }catch (Exception exception){
                        //it never happen
                        System.out.println(exception.getMessage());
                    }
                }
            }
        }
        try (FileOutputStream write=new FileOutputStream(Paths.get("ProgramData.txt").toFile());
             ObjectOutputStream writeData=new ObjectOutputStream(write)){
            writeData.writeObject(data);
            writeData.flush();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void setProgram(MouseEvent mouseEvent) {
        Channel channel=null;
        try {
            channel=data.getChannelWithName(channelListView.getSelectionModel().getSelectedItem());
            for (int i = 0; i < channel.getPrograms().size(); i++) {
                programListView.getItems().add(channel.getPrograms().get(i).getName());
            }
        }catch (Exception e){
            //if (channel == null)
            System.out.println(e.getMessage());
        }
    }

    public void back(MouseEvent mouseEvent) {
        try {
            new PageLoader().load("/View/Page.fxml");
        }catch (Exception e){
            //it never happen
            System.out.println(e.getMessage());
        }
    }
}
