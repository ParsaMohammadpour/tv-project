package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import Model.Channel.Channel;
import Model.Data.Data;
import Model.MainPage.*;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;

public class AddChannelController {
    private Data data;

    @FXML
    TextField channelNameTextField;

    @FXML
    Label channelNameLabel;

    @FXML
    ListView<String>managerListView;

    @FXML
    Button selectManagerButton;

    @FXML
    Button makeButton;

    @FXML
    Button backButton;

    public void initialize(){
        data=LoginPageController.getData();
        for (int i = 0; i < data.getManagers().size(); i++) {
            try {
                if (data.getManagers().get(i).getChannel()==null)
                    managerListView.getItems().add(data.getManagers().get(i).getUsername()+"  "+data.getManagers().get(i).getName());
            }catch (Exception e){
                //it never happen
                System.out.println(e.getMessage());
            }
        }
        managerListView.setVisible(false);
        System.out.println(channelNameTextField.getText());
        channelNameTextField.setText(null);
        channelNameLabel.setVisible(false);
    }


    public void back(MouseEvent mouseEvent) {
        try {
            new PageLoader().load("/View/Page.fxml");
        }catch (Exception e){
            //it never happen
            System.out.println(e.getMessage());
        }
    }

    public void make(MouseEvent mouseEvent) {
        channelNameLabel.setVisible(false);
        if (channelNameTextField.getText()==null){
            channelNameLabel.setVisible(true);
            return;
        }
        try {
            Channel channel=data.getChannelWithName(channelNameTextField.getText());
            channelNameLabel.setVisible(true);
            return;
        }catch (Exception e){
            if (e.getMessage().equals("NO SUCH CHANNEL!")){
                Channel channel=null;
                if (managerListView.getSelectionModel().getSelectedItem()==null||(!managerListView.isVisible())){
                    channel =new Channel(channelNameTextField.getText(),data.getGeneralManager());
                }else {
                    try {
                        channel=new Channel(channelNameTextField.getText(),data.getGeneralManager()
                                ,data.getManagerWithUsername(managerListView.getSelectionModel().getSelectedItem()));
                    }catch (Exception exception){
                        //it never happen
                        System.out.println(exception.getMessage());
                        System.out.println("%&%&%&%&%&%&%%&%&%%");
                    }
                }
                data.getChannels().add(channel);
                try (FileOutputStream write=new FileOutputStream(Paths.get("ProgramData.txt").toFile());
                     ObjectOutputStream writeData=new ObjectOutputStream(write)){
                    writeData.writeObject(data);
                    writeData.flush();
                }catch (Exception exception){
                    //it never happen
                    System.out.println(exception.getMessage());
                }
            }
        }
    }

    public void backEnter(MouseEvent mouseEvent) {
        backButton.setUnderline(true);
    }


    public void backExit(MouseEvent mouseEvent) {
        backButton.setUnderline(false);
    }

    public void selectEnter(MouseEvent mouseEvent) {
        selectManagerButton.setUnderline(true);
    }

    public void selectExit(MouseEvent mouseEvent) {
        selectManagerButton.setUnderline(false);
    }

    public void selectManager(MouseEvent mouseEvent) {
        if (managerListView.isVisible()) {
            managerListView.setVisible(false);
        } else {
            managerListView.setVisible(true);
        }
    }

    public void makeEnter(MouseEvent mouseEvent) {
        makeButton.setUnderline(true);
    }

    public void makeExit(MouseEvent mouseEvent) {
        makeButton.setUnderline(false);
    }
}