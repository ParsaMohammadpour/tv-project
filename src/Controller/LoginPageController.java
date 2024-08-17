package Controller;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import Model.Channel.Channel;
import Model.Data.Data;
import Model.Person.GeneralManager;
import Model.Program.Movie;
import Model.Program.Program;
import Model.Time.Date;
import Model.Time.Time;
import Model.MainPage.*;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;

public class LoginPageController {
    private static Data data;

    public static Data getData() {
        return data;
    }

    public static String activeUsername;

    @FXML
    Button loginButton;

    @FXML
    TextField usernameField;

    @FXML
    PasswordField passwordField;

    @FXML
    TextField textFieldPassword;

    @FXML
    Label wrongUsernameLabel;

    @FXML
    Label wrongPasswordLabel;

    @FXML
    ImageView visibilityImage;

    @FXML
    Label usernameEnterLabel;

    @FXML
    Label passwordEnterLabel;

    @FXML
    ImageView unvisibilityImage;

    @FXML
    Button registerPageButton;

    public void initialize() {
        TranslateTransition translateTransition = new TranslateTransition
                (Duration.millis(1300), loginButton);
        translateTransition.setToY(-230);
        translateTransition.playFromStart();
        activeUsername = null;
        usernameField.setText(null);
        passwordField.setText(null);
        textFieldPassword.setText(null);
        passwordField.setVisible(true);
        textFieldPassword.setVisible(false);
        visibilityImage.setVisible(false);
        unvisibilityImage.setVisible(true);
        try {
            if (Paths.get("ProgramData.txt").toFile().exists()) {
                FileInputStream readFile = new FileInputStream(Paths.get("ProgramData.txt").toFile());
                ObjectInputStream readData = new ObjectInputStream(readFile);
                data = (Data) readData.readObject();
                readData.close();
            } else {
                FileOutputStream writeFile = new FileOutputStream(Paths.get("ProgramData.txt").toFile());
                ObjectOutputStream writeData = new ObjectOutputStream(writeFile);
                GeneralManager generalManager = new GeneralManager("PARSA",
                        "MOHAMMADPOUR", "PARSA", "PARSA");
                Time start = new Time(22, 0, 0);
                Time finish = new Time(23, 30, 0);
                Date date = new Date(1399, 3, 20);
                Date date1 = new Date(1399, 3, 27);
                Movie movie = new Movie("movie", start, finish,start, finish, date,date1);
                ArrayList<Program> movies = new ArrayList<>();
                movies.add(movie);
                Channel channel = new Channel("channel1", generalManager, null, movies);
                ArrayList<Channel> channels = new ArrayList<>();
                channels.add(channel);
                generalManager.setChannels(channels);
                data = new Data(generalManager);
                data.getPrograms().add(movie);
                writeData.writeObject(data);
                writeData.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void login(ActionEvent actionEvent) throws Exception {
        wrongUsernameLabel.setVisible(false);
        wrongPasswordLabel.setVisible(false);
        usernameEnterLabel.setVisible(false);
        passwordEnterLabel.setVisible(false);
        if (usernameField.getText() == null) {
            usernameEnterLabel.setVisible(true);
            return;
        }
        if (passwordField.isVisible() && passwordField.getText() == null) {
            passwordEnterLabel.setVisible(true);
            return;
        }
        if (textFieldPassword.isVisible() && textFieldPassword.getText() == null) {
            passwordEnterLabel.setVisible(true);
            return;
        }
        if (usernameField.getText().equals(data.getGeneralManager().getUsername())) {
            if (!textFieldPassword.isVisible()) {
                if (passwordField.getText().equals(data.getGeneralManager().getPassword())) {
                    PageController.allowToEdit = true;
                    activeUsername = data.getGeneralManager().getUsername();
                    new PageLoader().load("/View/Page.fxml");
                    return;
                } else {
                    wrongPasswordLabel.setVisible(true);
                    return;
                }
            } else {
                if (textFieldPassword.getText().equals(data.getGeneralManager().getPassword())) {
                    PageController.allowToEdit = true;
                    activeUsername = data.getGeneralManager().getUsername();
                    new PageLoader().load("/View/Page.fxml");
                    return;
                } else {
                    wrongPasswordLabel.setVisible(true);
                    return;
                }
            }
        } else {
            for (int i = 0; i < data.getUsers().size(); i++) {
                if (usernameField.getText().equals(data.getUsers().get(i).getUsername())) {
                    if (passwordField.isVisible()) {
                        if (passwordField.getText().equals(data.getUsers().get(i).getPassword())) {
                            activeUsername = data.getUsers().get(i).getUsername();
                            PageController.allowToEdit = false;
                            new PageLoader().load("/View/Page.fxml");
                        } else {
                            wrongPasswordLabel.setVisible(true);
                        }
                    } else {
                        if (textFieldPassword.getText().equals(data.getUsers().get(i).getPassword())) {
                            activeUsername = data.getUsers().get(i).getUsername();
                            PageController.allowToEdit = false;
                            new PageLoader().load("/View/Page.fxml");
                        } else {
                            wrongPasswordLabel.setVisible(true);
                        }
                    }
                    return;
                }
            }
            for (int i = 0; i < data.getManagers().size(); i++) {
                if (usernameField.getText().equals(data.getManagers().get(i).getUsername())) {
                    if (!textFieldPassword.isVisible()) {
                        if (passwordField.getText().equals(data.getManagers().get(i).getPassword())) {
                            activeUsername = data.getManagers().get(i).getUsername();
                            PageController.allowToEdit = true;
                            new PageLoader().load("/View/Page.fxml");
                            return;
                        } else {
                            wrongPasswordLabel.setVisible(true);
                            return;
                        }
                    } else {
                        if (textFieldPassword.getText().equals(data.getManagers().get(i).getPassword())) {
                            activeUsername = data.getManagers().get(i).getUsername();
                            PageController.allowToEdit = true;
                            new PageLoader().load("/View/Page.fxml");
                            return;
                        } else {
                            wrongPasswordLabel.setVisible(true);
                            return;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < data.getCompanies().size(); i++) {
            if (textFieldPassword.isVisible()&&data.getCompanies().get(i).getName().equals(usernameField.getText()) &&
                    textFieldPassword.getText().equals(data.getCompanies().get(i).getPassword())) {
                try {
                    LoginPageController.activeUsername=data.getCompanies().get(i).getName();
                    new PageLoader().load("/View/CompanyPage.fxml");
                } catch (Exception e) {
                    //it never happen
                    System.out.println(e.getMessage());
                }
            }
            if (passwordField.isVisible()&&data.getCompanies().get(i).getName().equals(usernameField.getText()) &&
                    passwordField.getText().equals(data.getCompanies().get(i).getPassword())) {
                try {
                    LoginPageController.activeUsername=data.getCompanies().get(i).getName();
                    new PageLoader().load("/View/CompanyPage.fxml");
                } catch (Exception e) {
                    //it never happen
                    System.out.println(e.getMessage());
                }
            }
        }
        wrongUsernameLabel.setVisible(true);
        usernameField.setText(null);
        passwordField.setText(null);
        textFieldPassword.setText(null);
    }

    public void showPassword() {
        if (passwordField.isVisible()) {
            textFieldPassword.setText(passwordField.getText());
            passwordField.setVisible(false);
            textFieldPassword.setVisible(true);
        } else {
            passwordField.setText(textFieldPassword.getText());
            passwordField.setVisible(true);
            textFieldPassword.setVisible(false);
        }
    }

    public void clickVisible(MouseEvent mouseEvent) {
        showPassword();
        if (unvisibilityImage.isVisible()) {
            unvisibilityImage.setVisible(false);
            visibilityImage.setVisible(true);
        } else {
            unvisibilityImage.setVisible(true);
            visibilityImage.setVisible(false);
        }
    }

    public void gotoRegisterPage(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("/View/RegisterPage.fxml");
    }

    public void changeColorEnter(MouseEvent mouseEvent) {
        registerPageButton.setUnderline(true);
    }

    public void changeColorExit(MouseEvent mouseEvent) {
        registerPageButton.setUnderline(false);
    }
}