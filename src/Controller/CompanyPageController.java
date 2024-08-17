package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import Model.Channel.Channel;
import Model.Data.Data;
import Model.Program.Advertisement.Advertisement;
import Model.Program.Advertisement.Company;
import Model.Request.Request;
import Model.Time.Date;
import Model.Time.Time;
import Model.MainPage.*;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;

public class CompanyPageController {
    private Data data;

    @FXML
    ListView<String> channelListView;

    @FXML
    TextField nameTextField;

    @FXML
    TextField s_HourTextField;

    @FXML
    TextField s_MinuetTextField;

    @FXML
    TextField s_SecondTextField;

    @FXML
    TextField f_HourTextField;

    @FXML
    TextField f_MinuetTextField;

    @FXML
    TextField f_SecondTextField;

    @FXML
    TextField yearTextField;

    @FXML
    TextField monthTextField;

    @FXML
    TextField dayTextField;

    @FXML
    Button logoutButton;

    @FXML
    Button makeButton;

    @FXML
    Label doneLabel;

    @FXML
    Label errorLabel;

    @FXML
    CheckBox betweenProgramCheckBox;

    @FXML
    TextField moneyTextField;

    public void initialize() {
        data = LoginPageController.getData();
        for (int i = 0; i < data.getChannels().size(); i++) {
            channelListView.getItems().add(data.getChannels().get(i).getName());
        }
        dayTextField.setText(null);
        f_HourTextField.setText(null);
        f_MinuetTextField.setText(null);
        f_SecondTextField.setText(null);
        monthTextField.setText(null);
        nameTextField.setText(null);
        s_HourTextField.setText(null);
        s_MinuetTextField.setText(null);
        s_SecondTextField.setText(null);
        yearTextField.setText(null);
        Company company=null;
        for (int i = 0; i < data.getCompanies().size(); i++) {
            if (data.getCompanies().get(i).getName().equals(LoginPageController.activeUsername)){
                company=data.getCompanies().get(i);
            }
        }
        String money=String.valueOf(company.getMoney());
        moneyTextField.setText(money);
        System.out.println(company.getMoney());
        moneyTextField.setDisable(true);
    }

    public void make(MouseEvent mouseEvent) {
        doneLabel.setVisible(false);
        errorLabel.setVisible(false);
        if (yearTextField.getText() == null || monthTextField.getText() == null || dayTextField.getText() == null ||
                s_SecondTextField.getText() == null || s_MinuetTextField.getText() == null || s_HourTextField.getText() == null ||
                f_HourTextField.getText() == null || f_SecondTextField.getText() == null || f_MinuetTextField.getText() == null ||
                channelListView.getSelectionModel().getSelectedItem() == null) {
            errorLabel.setVisible(true);
            return;
        }
        try {
            Channel channel = data.getChannelWithName(channelListView.getSelectionModel().getSelectedItem());
            Time time1 = new Time(Integer.parseInt(s_HourTextField.getText()), Integer.parseInt(s_MinuetTextField.getText()),
                    Integer.parseInt(s_SecondTextField.getText()));
            Time time2 = new Time(Integer.parseInt(f_HourTextField.getText()), Integer.parseInt(f_MinuetTextField.getText()),
                    Integer.parseInt(f_SecondTextField.getText()));
            Date date = new Date(Integer.parseInt(yearTextField.getText()), Integer.parseInt(monthTextField.getText()),
                    Integer.parseInt(dayTextField.getText()));
            Company company = null;
            for (int i = 0; i < data.getCompanies().size(); i++) {
                if (data.getCompanies().get(i).getName().equals(LoginPageController.activeUsername)) {
                    company = data.getCompanies().get(i);
                    System.out.println("CCOOOOOOOOOOOOOOOOOOOOOOMPANY");
                    break;
                }
            }
            Advertisement advertisement = new Advertisement(nameTextField.getText(), time1, time2
                    , null, company, 0, date);
            Request request = new Request(advertisement);
            channel.addRequest(request);
            System.out.println("YESSSSSSSSSSSSSSSSSSSSSSSSs");
        } catch (Exception e) {
            errorLabel.setVisible(true);
            System.out.println(e.getMessage());
            e.printStackTrace();
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

    public void logOut(MouseEvent mouseEvent) {
        try {
            new PageLoader().load("/View/LoginPage.fxml");
        }catch (Exception e){
            //it never happen
            System.out.println(e.getMessage());
        }
    }
}
