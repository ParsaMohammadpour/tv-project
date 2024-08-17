package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import Model.Channel.Channel;
import Model.Data.Data;
import Model.Person.GeneralManager;
import Model.Person.User;
import Model.Program.Movie;
import Model.Program.Program;
import Model.Time.Date;
import Model.Time.Time;
import Model.MainPage.*;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;

public class RegisterPageController {
    private Data data;

    @FXML
    ImageView visibleImage;

    @FXML
    ImageView unvisibleImage;

    @FXML
    TextField username;

    @FXML
    TextField name;

    @FXML
    TextField lastname;

    @FXML
    TextField passwordText1;

    @FXML
    TextField passwordText2;

    @FXML
    PasswordField password1;

    @FXML
    PasswordField password2;

    @FXML
    Button registerButton;

    @FXML
    Button loginPageButton;

    @FXML
    ImageView visibleImage2;

    @FXML
    ImageView unvisibleImage2;

    @FXML
    Label nullNmaeLabel;

    @FXML
    Label nullLastnameLabel;

    @FXML
    Label nullUsernameLabel;

    @FXML
    Label nullPasswordLabel;

    @FXML
    Label nullPassword2Label;

    @FXML
    Label invalidUsernameLabel;

    @FXML
    Label invalidPassword;

    @FXML
    Label doesntmatchPasswordLabel;

    public void initialize(){
        username.setText(null);
        name.setText(null);
        lastname.setText(null);
        password1.setText(null);
        password2.setText(null);
        passwordText1.setText(null);
        passwordText1.setVisible(false);
        passwordText2.setVisible(false);
        visibleImage2.setVisible(false);
        visibleImage.setVisible(false);
        unvisibleImage2.setVisible(true);
        unvisibleImage.setVisible(true);
        password2.setVisible(true);
        password1.setVisible(true);
        passwordText2.setText(null);
        LoginPageController.activeUsername=null;
        data =LoginPageController.getData();
    }

    public void mouseEntre(MouseEvent mouseEvent) {
        loginPageButton.setUnderline(true);
    }

    public void mouseExit(MouseEvent mouseEvent) {
        loginPageButton.setUnderline(false);
    }

    public void setUnvisible(MouseEvent mouseEvent) {
        visibleImage.setVisible(false);
        unvisibleImage.setVisible(true);
        password1.setText(passwordText1.getText());
        passwordText1.setVisible(false);
        password1.setVisible(true);
    }

    public void setVisible(MouseEvent mouseEvent) {
        visibleImage.setVisible(true);
        unvisibleImage.setVisible(false);
        passwordText1.setText(password1.getText());
        passwordText1.setVisible(true);
        password1.setVisible(false);
    }

    public void setUnvisible2(MouseEvent mouseEvent) {
        visibleImage2.setVisible(false);
        unvisibleImage2.setVisible(true);
        password2.setText(passwordText2.getText());
        passwordText2.setVisible(false);
        password2.setVisible(true);
    }

    public void setVisible2(MouseEvent mouseEvent) {
        visibleImage2.setVisible(true);
        unvisibleImage2.setVisible(false);
        passwordText2.setText(password2.getText());
        passwordText2.setVisible(true);
        password2.setVisible(false);
    }

    private void setTextsNull(){
        name.setText(null);
        lastname.setText(null);
        username.setText(null);
        password2.setText(null);
        password1.setText(null);
        passwordText2.setText(null);
        passwordText1.setText(null);
    }

    public void registering(MouseEvent mouseEvent) throws IOException {
        nullNmaeLabel.setVisible(false);
        nullLastnameLabel.setVisible(false);
        nullUsernameLabel.setVisible(false);
        nullPassword2Label.setVisible(false);
        nullPasswordLabel.setVisible(false);
        invalidPassword.setVisible(false);
        invalidUsernameLabel.setVisible(false);
        doesntmatchPasswordLabel.setVisible(false);
        if(name.getText()==null){
            nullNmaeLabel.setVisible(true);
            setTextsNull();
            return;
        }
        if(lastname.getText()==null){
            nullLastnameLabel.setVisible(true);
            setTextsNull();
            return;
        }
        if(username.getText()==null){
            nullUsernameLabel.setVisible(true);
            setTextsNull();
            return;
        }
        if(password1.getText()==null && passwordText1.getText()==null){
            nullPasswordLabel.setVisible(true);
            setTextsNull();
            return;
        }
        if(password2.getText()==null && passwordText2.getText()==null){
            nullPassword2Label.setVisible(true);
            setTextsNull();
            return;
        }
        if(username.getText().equals(data.getGeneralManager().getUsername())){
            invalidUsernameLabel.setVisible(true);
            setTextsNull();
            return;
        }
        try {
            if (password1.isVisible()&&password2.isVisible()) {
                data.registerUser(name.getText(), lastname.getText(), username.getText(), password1.getText(), password2.getText());
            }
            else if(password1.isVisible()){
                data.registerUser(name.getText(), lastname.getText(), username.getText(), password1.getText(),
                        passwordText2.getText());
            }
            else if (password2.isVisible()){
                data.registerUser(name.getText(), lastname.getText(), username.getText(), passwordText1.getText(),
                        password2.getText());
            }
            else {
                data.registerUser(name.getText(), lastname.getText(), username.getText(), passwordText1.getText(),
                        passwordText2.getText());
            }
        }catch (Exception e){
            if(e.getMessage().equals("PASSWORDS DOESNT MATCHE!")){
                doesntmatchPasswordLabel.setVisible(true);
                setTextsNull();
                return;
            }
            if(e.getMessage().equals("THIS USERNAME ALREADY HAS BEEN TAKEN!")){
                invalidUsernameLabel.setVisible(true);
                setTextsNull();
                return;
            }
            if(e.getMessage().equals("PASSWORD MUST BE ATLEAST 5 CHARACTER!")){
                invalidPassword.setVisible(true);
                setTextsNull();
                return;
            }
        }
        try {
            FileOutputStream write =new FileOutputStream(Paths.get("ProgramData.txt").toFile());
            ObjectOutputStream writeData =new ObjectOutputStream(write);
            writeData.writeObject(data);
            writeData.flush();
            writeData.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        LoginPageController.activeUsername=username.getText();
        PageController.allowToEdit=false;
        new PageLoader().load("/View/Page.fxml");
    }

    public void loginPage(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("/View/LoginPage.fxml");
    }

    public void reset(MouseEvent mouseEvent) {
        setTextsNull();
    }
}
