package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import Model.Channel.Channel;
import Model.Data.Data;
import Model.Person.Manager;
import Model.Request.Request;
import Model.Request.Status;
import Model.MainPage.*;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;

public class RequestStatusPageController {
    private Data data;

    private boolean isGeneralManager = false;

    @FXML
    ListView<String> channelListView;

    @FXML
    ListView<String> requestListView;

    @FXML
    Label errorLabel;

    @FXML
    Label doneLabel;

    @FXML
    Button saveButton;

    @FXML
    Button backButton;

    @FXML
    ChoiceBox<String> statusChoiceBox;

    public void initialize() {
        data = LoginPageController.getData();
        if (data.getGeneralManager().getUsername().equals(LoginPageController.activeUsername)) isGeneralManager = true;
        if (!isGeneralManager) {
            channelListView.setVisible(false);
            for (int i = requestListView.getItems().size()-1; i >=0 ; i--) {
                requestListView.getItems().remove(i);
            }
            Channel channel=null;
            try {
                channel=data.getManagerWithUsername(LoginPageController.activeUsername).getChannel();
            }catch (Exception e){
                //it never happen
                System.out.println(e.getMessage());
            }
            for (int i = 0; i < channel.getRequests().size(); i++) {
                requestListView.getItems().add(channel.getRequests().get(i).getAdvertisement().getName()
                        +" "+channel.getRequests().get(i).getStatus());
            }
            requestListView.setVisible(true);
        } else {
            for (int i = 0; i < data.getChannels().size(); i++) {
                channelListView.getItems().add(data.getChannels().get(i).getName());
            }
        }
        statusChoiceBox.getItems().add("Accept");
        statusChoiceBox.getItems().add("Reject");
    }


    public void back(MouseEvent mouseEvent) {
        try {
            new PageLoader().load("/View/Page.fxml");
        } catch (Exception e) {
            //it never happen
            System.out.println(e.getMessage());
        }
    }

    public void save(MouseEvent mouseEvent) {
        errorLabel.setVisible(false);
        doneLabel.setVisible(false);
        if (isGeneralManager && channelListView.getSelectionModel().getSelectedItem() == null) {
            errorLabel.setVisible(true);
            return;
        }
        if (requestListView.getSelectionModel().getSelectedItem() == null) {
            errorLabel.setVisible(true);
            return;
        }
        if (isGeneralManager) {
            Channel channel = null;
            try {
                channel = data.getChannelWithName(channelListView.getSelectionModel().getSelectedItem());
            } catch (Exception e) {
                //it never happen
                System.out.println(e.getMessage());
                errorLabel.setVisible(true);
                return;
            }
            Request request = null;
            String name;
            for (int i = 0; i < channel.getRequests().size(); i++) {
                name=channel.getRequests().get(i).getAdvertisement().getName()+ " "+channel.getRequests().get(i).getStatus();
                if (name.equals((requestListView.getSelectionModel().getSelectedItem()))){
                    request = channel.getRequests().get(i);
                    System.out.println("request null nist");
                }
            }
            if (statusChoiceBox.getValue().equals("Accept")) {
                try {
                    System.out.println("***");
                    channel.acceptRequest(request);
                    System.out.println("**");
                    doneLabel.setVisible(true);
                    System.out.println("*");
                    data.getPrograms().add(request.getAdvertisement());
                } catch (Exception e) {
                    errorLabel.setVisible(true);
                    System.out.println(e.getMessage());
                    return;
                }
            } else if (statusChoiceBox.getValue().equals("Reject")) {
                channel.rejectRequest(request);
                doneLabel.setVisible(true);
            } else {
                errorLabel.setVisible(true);
                return;
            }
        } else {
            Manager manager = null;
            try {
                manager = data.getManagerWithUsername(LoginPageController.activeUsername);
            } catch (Exception e) {
                //it never happen
                System.out.println(e.getMessage());
            }
            Channel channel = null;
            try {
                channel = manager.getChannel();
            } catch (Exception e) {
                //it never happen
                System.out.println(e.getMessage());
            }
            Request request = null;
            String requestName;
            for (int i = 0; i < channel.getRequests().size(); i++) {
                requestName=channel.getRequests().get(i).getAdvertisement().getName()+" "+channel.getRequests().get(i).getStatus();
                if (!channel.getRequests().get(i).getStatus().equals(Status.PENDING)){
                    continue;
                }
                if (requestName.equals(requestListView.getSelectionModel().getSelectedItem())) {
                    request = channel.getRequests().get(i);
                    break;
                }
            }
            if (statusChoiceBox.getValue().equals("Accept")) {
                try {
                    channel.acceptRequest(request);
                    data.getPrograms().add(request.getAdvertisement());
                    doneLabel.setVisible(true);
                } catch (Exception e) {
                    errorLabel.setVisible(true);
                    return;
                }
            } else if (statusChoiceBox.getValue().equals("Reject")) {
                channel.rejectRequest(request);
            } else {
                errorLabel.setVisible(false);
                return;
            }
        }
        try (FileOutputStream write = new FileOutputStream(Paths.get("ProgramData.txt").toFile());
             ObjectOutputStream writeData = new ObjectOutputStream(write)) {
            writeData.writeObject(data);
            writeData.flush();
        } catch (Exception e) {
            //it never happen
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void showRequests(MouseEvent mouseEvent) {
        for (int i = requestListView.getItems().size()-1; i >=0 ; i--) {
            requestListView.getItems().remove(i);
        }
        if (isGeneralManager) {
            Channel channel = null;
            try {
                channel = data.getChannelWithName(channelListView.getSelectionModel().getSelectedItem());
            } catch (Exception e) {
                //it never happen
                System.out.println(e.getMessage());
            }
            for (int i = 0; i < channel.getRequests().size(); i++) {
                System.out.println("%^*%^&^&^^*%858585*58585"+channel.getRequests().get(i).getStatus());
                requestListView.getItems().add(channel.getRequests().get(i).getAdvertisement().getName()
                        + " " + channel.getRequests().get(i).getStatus());
            }
        }else {
            Channel channel=null;
            try {
                channel=data.getManagerWithUsername(LoginPageController.activeUsername).getChannel();
            }catch (Exception e){
                //it never happen
                System.out.println(e.getMessage());
            }
            for (int i = 0; i < channel.getRequests().size(); i++) {
                statusChoiceBox.getItems().add(channel.getRequests().get(i).getAdvertisement().getName()
                        +" "+channel.getRequests().get(i).getStatus());
            }
        }
        requestListView.setVisible(true);
    }

    public void acceptDelet(MouseEvent mouseEvent) {
        if (isGeneralManager){
            for (int i = 0; i < data.getChannels().size(); i++) {
                for (int j = 0; j < data.getChannels().get(i).getRequests().size(); j++) {
                    if (data.getChannels().get(i).getRequests().get(j).getStatus().equals(Status.ACCEPTED)){
                        data.getChannels().get(i).getRequests().remove(j);
                    }
                }
            }
        }else {
            Channel channel=null;
            try {
                data.getManagerWithUsername(LoginPageController.activeUsername).getChannel();
            }catch (Exception e){
                //it never happen
                System.out.println(e.getMessage());
            }
            for (int i = 0; i < channel.getRequests().size(); i++) {
                if (channel.getRequests().get(i).getStatus().equals(Status.ACCEPTED)){
                    channel.getRequests().remove(i);
                }
            }
            for (int i = requestListView.getItems().size()-1; i >=0 ; i--) {
                requestListView.getItems().remove(i);
            }
            for (int i = 0; i < channel.getRequests().size(); i++) {
                requestListView.getItems().add(channel.getRequests().get(i).getAdvertisement().getName()+" "+
                        channel.getRequests().get(i).getStatus());
            }
        }
        try (FileOutputStream write = new FileOutputStream(Paths.get("ProgramData.txt").toFile());
             ObjectOutputStream writeData = new ObjectOutputStream(write)) {
            writeData.writeObject(data);
            writeData.flush();
        } catch (Exception e) {
            //it never happen
            System.out.println(e.getMessage());
        }
    }


    public void rejectDelet(MouseEvent mouseEvent) {
        if (isGeneralManager){
            for (int i = 0; i < data.getChannels().size(); i++) {
                data.getChannels().get(i).removeRejectedRequest();
            }
        }else {
            Channel channel=null;
            try {
                channel=data.getManagerWithUsername(LoginPageController.activeUsername).getChannel();
            }catch (Exception e){
                //it never happen
                System.out.println(e.getMessage());
            }
            channel.removeRejectedRequest();
        }
        try (FileOutputStream write = new FileOutputStream(Paths.get("ProgramData.txt").toFile());
             ObjectOutputStream writeData = new ObjectOutputStream(write)) {
            writeData.writeObject(data);
            writeData.flush();
        } catch (Exception e) {
            //it never happen
            System.out.println(e.getMessage());
        }
    }


    public void doneDelet(MouseEvent mouseEvent) {
        if (isGeneralManager){
            for (int i = 0; i < data.getChannels().size(); i++) {
                data.getChannels().get(i).removeDoneRequest();
            }
        }else {
            Channel channel=null;
            try {
                channel=data.getManagerWithUsername(LoginPageController.activeUsername).getChannel();
            }catch (Exception e){
                //it never happen
                System.out.println(e.getMessage());
            }
            channel.removeDoneRequest();
        }
        try (FileOutputStream write = new FileOutputStream(Paths.get("ProgramData.txt").toFile());
             ObjectOutputStream writeData = new ObjectOutputStream(write)) {
            writeData.writeObject(data);
            writeData.flush();
        } catch (Exception e) {
            //it never happen
            System.out.println(e.getMessage());
        }
    }
}