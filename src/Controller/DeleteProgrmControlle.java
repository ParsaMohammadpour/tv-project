package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import Model.Channel.Channel;
import Model.Data.Data;
import Model.MainPage.*;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;

public class DeleteProgrmControlle {
    private Data data;

    @FXML
    ListView<String>channelListView;

    @FXML
    ListView<String>programListView;

    @FXML
    Button backButton;


    @FXML
    Button removeButton;

    public void initialize(){
        data=LoginPageController.getData();
        for (int i = 0; i < data.getChannels().size(); i++) {
            channelListView.getItems().add(data.getChannels().get(i).getName());
        }
        programListView.setVisible(false);
    }


    public void showProgram(MouseEvent mouseEvent) {
        if (channelListView.getSelectionModel().getSelectedItem()==null){
            return;
        }
        try {
            for (int i = 0; i < data.getChannelWithName(channelListView.getSelectionModel()
                    .getSelectedItem()).getPrograms().size(); i++) {
                programListView.getItems().add(data.getChannelWithName(channelListView.getSelectionModel()
                        .getSelectedItem()).getPrograms().get(i).getName());
            }
        }catch (Exception e){
            // it never happen
            System.out.println(e.getMessage());
        }
        programListView.setVisible(true);
    }

    public void backEnter(MouseEvent mouseEvent) {
        backButton.setUnderline(true);
    }

    public void backExit(MouseEvent mouseEvent) {
        backButton.setUnderline(false);
    }

    public void removeEnter(MouseEvent mouseEvent) {
        removeButton.setUnderline(true);
    }

    public void removeExit(MouseEvent mouseEvent) {
        removeButton.setUnderline(false);
    }

    public void back(MouseEvent mouseEvent) {
        try {
            new PageLoader().load("/View/Page.fxml");
        }catch (Exception e){
            //it never happen
            System.out.println(e.getMessage());
        }
    }

    public void remove(MouseEvent mouseEvent) {
        if (programListView.getSelectionModel().getSelectedItem()==null){
            return;
        }
        try {
            Channel channel = data.getChannelWithName(channelListView.getSelectionModel().getSelectedItem());
            for (int i = 0; i < channel.getPrograms().size(); i++) {
                if (programListView.getSelectionModel().getSelectedItem().equals(channel.getPrograms().get(i).getName())){
                    channel.getPrograms().remove(i);
                }
            }
            for (int i = 0; i < data.getPrograms().size(); i++) {
                if (data.getPrograms().get(i).getName().equals(programListView.getSelectionModel().getSelectedItem())){
                    data.getPrograms().remove(i);
                }
                }
            programListView.getItems().remove(programListView.getSelectionModel().getSelectedItem());
            } catch (Exception e) {
            //it never happen
            System.out.println(e.getMessage());
        }
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
