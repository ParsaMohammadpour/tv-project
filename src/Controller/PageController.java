package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import Model.Channel.Channel;
import Model.Data.Data;
import Model.Person.User;
import Model.Program.*;
import Model.Program.Advertisement.Advertisement;
import Model.Time.Time;
import Model.MainPage.*;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;

public class PageController {
    private Data data;

    private Program program;

    public static boolean allowToEdit;

    @FXML
    ListView<String> channelsListView;

    @FXML
    ImageView visibleImage;

    @FXML
    ImageView visibleImage2;

    @FXML
    ImageView unvisibleImage;

    @FXML
    ImageView unvisibleImage2;

    @FXML
    TextField passwordText1;

    @FXML
    TextField passwordText2;

    @FXML
    PasswordField password1;

    @FXML
    PasswordField password2;

    @FXML
    AnchorPane settingPane;

    @FXML
    TextField name;

    @FXML
    TextField lastname;

    @FXML
    TextField newUsername;

    @FXML
    Button doneButton;

    @FXML
    Label invalidPasswordLabel;

    @FXML
    Label invalidUsernameLabel;

    @FXML
    Label successfulLabel;

    @FXML
    Button backButton;

    @FXML
    ListView<String> programListView;

    @FXML
    TextArea programInfoTextArea;

    @FXML
    ChoiceBox<Integer> voteChoiceBox;

    @FXML
    Label voteLabel;

    @FXML
    Button voteButton;

    @FXML
    Label selectNumberLabel;

    @FXML
    TextField searchTextField;

    @FXML
    ImageView searchImageView;

    @FXML
    Label searchLabel;

    @FXML
    Button logOutButton;

    @FXML
    ChoiceBox<String> addChoiceBox;

    @FXML
    ImageView TVImageView;

    public void initialize() {
        TVImageView.setVisible(false);
        settingPane.setVisible(false);
        visibleImage.setVisible(false);
        visibleImage2.setVisible(false);
        unvisibleImage.setVisible(false);
        unvisibleImage2.setVisible(false);
        name.setVisible(false);
        lastname.setVisible(false);
        newUsername.setVisible(false);
        password2.setVisible(false);
        password1.setVisible(false);
        passwordText1.setVisible(false);
        passwordText2.setVisible(false);
        invalidPasswordLabel.setVisible(false);
        invalidUsernameLabel.setVisible(false);
        successfulLabel.setVisible(false);
        selectNumberLabel.setVisible(false);
        programInfoTextArea.setVisible(false);
        data = LoginPageController.getData();
        for (int i = 0; i < data.getChannels().size(); i++) {
            channelsListView.getItems().add(data.getChannels().get(i).getName());
        }
        programListView.setVisible(false);
        for (int i = 0; i < 6; i++) {
            voteChoiceBox.getItems().add(i);
        }
        voteChoiceBox.setVisible(false);
        voteLabel.setVisible(false);
        if (allowToEdit) {
            addChoiceBox.getItems().add("Program");
            addChoiceBox.getItems().add("Request");
            addChoiceBox.getItems().add("Presenter");
            addChoiceBox.getItems().add("Formula");
            addChoiceBox.getItems().add("Delete");
            if (LoginPageController.activeUsername.equals(data.getGeneralManager().getUsername())) {
                addChoiceBox.getItems().add("Channel");
                addChoiceBox.getItems().add("Manager");
                addChoiceBox.getItems().add("Company");
            }
            addChoiceBox.setVisible(true);
            TVImageView.setVisible(true);
        } else {
            addChoiceBox.setDisable(true);
            addChoiceBox.setVisible(false);
            TVImageView.setDisable(true);
            TVImageView.setVisible(false);
        }
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

    public void makeChanges(MouseEvent mouseEvent) {
        invalidUsernameLabel.setVisible(false);
        invalidPasswordLabel.setVisible(false);
        successfulLabel.setVisible(false);
        if ((password1.isVisible() && password1.getText().length() < 5) ||
                (password2.isVisible() && password2.getText().length() < 5) ||
                (passwordText2.isVisible() && passwordText2.getText().length() < 5) ||
                (passwordText1.isVisible() && passwordText1.getText().length() < 5)) {
            invalidPasswordLabel.setVisible(true);
            return;
        }
        if (password1.isVisible()) {
            if (password2.isVisible()) {
                if (!password2.getText().equals(password1.getText())) {
                    invalidPasswordLabel.setVisible(true);
                    return;
                }
            } else {
                if (!password1.getText().equals(passwordText2.getText())) {
                    invalidPasswordLabel.setVisible(true);
                    return;
                }
            }
        } else {
            if (password2.isVisible()) {
                if (!password2.getText().equals(passwordText1.getText())) {
                    invalidPasswordLabel.setVisible(true);
                    return;
                }
            } else {
                if (!passwordText1.getText().equals(passwordText2.getText())) {
                    invalidPasswordLabel.setVisible(true);
                    return;
                }
            }
        }
        User user = null;
        for (int i = 0; i < data.getUsers().size(); i++) {
            if (data.getUsers().get(i).getUsername().equals(LoginPageController.activeUsername)) {
                user = data.getUsers().get(i);
                break;
            }
        }
        if (user == null) {
            System.out.println("%%%%%%%%%%%%%%%%%%%%%%%");
            return;
        }
        if ((newUsername.getText() != null) && (!user.getUsername().equals(newUsername.getText()))) {
            try {
                data.changeUsername(user.getUsername(), user.getPassword(), newUsername.getText());
            } catch (Exception e) {
                System.out.println(e.getMessage());
                invalidUsernameLabel.setVisible(true);
                return;
            }
        }
        try {
            data.changeName(user.getUsername(), user.getPassword(), name.getText());
            data.changeLastname(user.getUsername(), lastname.getText(), user.getPassword());
            if (password1.isVisible()) {
                data.changePassword(user.getUsername(), user.getPassword(), password1.getText());
            } else {
                data.changePassword(user.getUsername(), user.getPassword(), passwordText1.getText());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        successfulLabel.setVisible(true);
        try (FileOutputStream write = new FileOutputStream(Paths.get("ProgramData.txt").toFile());
             ObjectOutputStream writeDta = new ObjectOutputStream(write)) {
            writeDta.writeObject(data);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        LoginPageController.activeUsername = newUsername.getText();
    }

    public void entered(MouseEvent mouseEvent) {
        doneButton.setUnderline(true);
    }

    public void getoSetting(MouseEvent mouseEvent) {
        successfulLabel.setVisible(false);
        channelsListView.setVisible(false);
        settingPane.setVisible(true);
        visibleImage.setVisible(false);
        visibleImage2.setVisible(false);
        unvisibleImage.setVisible(true);
        unvisibleImage2.setVisible(true);
        name.setVisible(true);
        lastname.setVisible(true);
        newUsername.setVisible(true);
        password1.setVisible(true);
        password2.setVisible(true);
        passwordText1.setVisible(false);
        passwordText2.setVisible(false);
        successfulLabel.setVisible(false);
        invalidUsernameLabel.setVisible(false);
        invalidPasswordLabel.setVisible(false);
        backButton.setVisible(true);
        doneButton.setVisible(true);
        voteChoiceBox.setVisible(false);
        selectNumberLabel.setVisible(false);
        voteLabel.setVisible(false);
        programListView.setVisible(false);
        programInfoTextArea.setVisible(false);
        searchLabel.setVisible(false);
        logOutButton.setVisible(false);
    }

    public void exited(MouseEvent mouseEvent) {
        doneButton.setUnderline(false);
    }

    public void back(MouseEvent mouseEvent) {
        successfulLabel.setVisible(false);
        name.setText(null);
        lastname.setText(null);
        newUsername.setText(null);
        password2.setText(null);
        password1.setText(null);
        passwordText2.setText(null);
        passwordText1.setText(null);
        settingPane.setVisible(false);
        visibleImage.setVisible(false);
        visibleImage2.setVisible(false);
        unvisibleImage.setVisible(false);
        unvisibleImage2.setVisible(false);
        name.setVisible(false);
        lastname.setVisible(false);
        newUsername.setVisible(false);
        password2.setVisible(false);
        password1.setVisible(false);
        passwordText1.setVisible(false);
        passwordText2.setVisible(false);
        invalidPasswordLabel.setVisible(false);
        invalidUsernameLabel.setVisible(false);
        successfulLabel.setVisible(false);
        settingPane.setVisible(false);
        channelsListView.setVisible(true);
        programListView.setVisible(false);
        searchLabel.setVisible(false);
        logOutButton.setVisible(true);
    }

    public void enteredBack(MouseEvent mouseEvent) {
        backButton.setUnderline(true);
    }

    public void exitedBack(MouseEvent mouseEvent) {
        backButton.setUnderline(false);
    }

    public void showProgramInfo(MouseEvent mouseEvent) {
        successfulLabel.setVisible(false);
        searchLabel.setVisible(false);
        programInfoTextArea.setDisable(false);
        programInfoTextArea.setText(null);
        program = null;
        if (programListView.getSelectionModel().getSelectedItem()==null){
            return;
        }
        try {
            System.out.println(programListView.getSelectionModel().getSelectedItems()+"###");
            program = data.getProgram(programListView.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (!(program instanceof Advertisement)) {
            voteChoiceBox.setVisible(true);
            voteChoiceBox.setVisible(true);
            voteLabel.setVisible(true);
            voteButton.setVisible(true);
        }else {
            voteChoiceBox.setVisible(false);
            voteLabel.setVisible(false);
            voteButton.setVisible(false);
        }
        programInfoTextArea.setText("Program name : " + program.getName() + "\n");
        if (program instanceof Movie) {
            Time programTime = program.getEndTime().timeBetweenTime(program.getStartTime());
            programInfoTextArea.setText(programInfoTextArea.getText() + "Start Time : " +
                    program.getStartTime().getHour() + " : " + program.getStartTime().getMinuet() +
                    " : " + program.getStartTime().getSecond() + "\n" +
                    "Program EndTime : " + ((Movie) program).getEndTime().getHour() + " : " +
                    ((Movie) program).getEndTime().getMinuet() + " : " +
                    ((Movie) program).getEndTime().getSecond() + "\n" +
                    "Reply Time : " + ((Movie) program).getReplayTimeStart().getHour() + " : " +
                    ((Movie) program).getReplayTimeStart().getMinuet() + " : " +
                    ((Movie) program).getReplayTimeStart().getSecond() + "\n" +
                    "Program Time : " + programTime.getHour() + " : " + programTime.getMinuet() + " : " + programTime.getSecond()
                    + "\n" + "Program Play Date : " + program.getDate().getYear() + "/" + program.getDate().getMonth() + "/" +
                    ((Movie) program).getDate().getDay() + "\n" +
                    "Program Replay Date : " + ((Movie) program).getReplyDate().getYear() + "/" +
                    ((Movie) program).getReplyDate().getMonth() + "/" +
                    ((Movie) program).getReplyDate().getDay() + "\n" + "Program Rate : " + program.getRate());
        }
        if (program instanceof Series) {
            Time programTime = program.getEndTime().timeBetweenTime(program.getStartTime());
            programInfoTextArea.setText("Episode : " + ((Series) program).getEpisode() + "\n" +
                    "Session : " + ((Series)program).getSession()+"\n"+
                    programInfoTextArea.getText() + "Start Time : " +
                    program.getStartTime().getHour() + " : " + program.getStartTime().getMinuet() +
                    " : " + program.getStartTime().getSecond() + "\n" +
                    "Program EndTime : " + ((Series) program).getEndTime().getHour() + " : " +
                    ((Series) program).getEndTime().getMinuet() + " : " +
                    ((Series) program).getEndTime().getSecond() + "\n" +
                    "Reply Time : " + ((Series) program).getReplayTimeStart().getHour() + " : " +
                    ((Series) program).getReplayTimeStart().getMinuet() + " : " +
                    ((Series) program).getReplayTimeStart().getSecond() + "\n" +
                    "Program Time : " + programTime.getHour() + " : " + programTime.getMinuet() + " : " + programTime.getSecond()
                    + "\n" + "Program Play Date : " + program.getDate().getYear() + "/" + program.getDate().getMonth() + "/" +
                    ((Series) program).getDate().getDay() + "\n" +
                    "Program Replay Date : " + ((Series) program).getReplyDate().getYear() + "/" +
                    ((Series) program).getReplyDate().getMonth() + "/" +
                    ((Series) program).getReplyDate().getDay() + "\n" + "Program Rate : " + program.getRate());
        }
        if (program instanceof News) {
            Time programTime = program.getEndTime().timeBetweenTime(program.getStartTime());
            programInfoTextArea.setText(programInfoTextArea.getText() +
                    ((News) program).getPresenter().getName() + "\t" + ((News) program).getPresenter()
                    .getLastname() + "\n" + programInfoTextArea.getText() + "Start Time : " +
                    program.getStartTime().getHour() + " : " + program.getStartTime().getMinuet() +
                    " : " + program.getStartTime().getSecond() + "\n" +
                    "Program EndTime : " + ((News) program).getEndTime().getHour() + " : " +
                    ((News) program).getEndTime().getMinuet() + " : " +
                    ((News) program).getEndTime().getSecond() + "\n" +
                    "Reply Time : " + ((News) program).getStartTime().getHour() + " : " +
                    ((News) program).getStartTime().getMinuet() + " : " +
                    ((News) program).getStartTime().getSecond() + "\n" +
                    "Program Time : " + programTime.getHour() + " : " + programTime.getMinuet() + " : " + programTime.getSecond()+
                    "\n" + "Program Rate : " + program.getRate());
        }
        if (program instanceof Entertainment) {
            Time programTime = program.getEndTime().timeBetweenTime(program.getStartTime());
            programInfoTextArea.setText(programInfoTextArea.getText() +
                    ((Entertainment) program).getPresenter().getName() + "\t" +
                    ((Entertainment) program).getPresenter().getLastname() + "\n" + "Start Time : " +
                    program.getStartTime().getHour() + " : " + program.getStartTime().getMinuet() +
                    " : " + program.getStartTime().getSecond() + "\n" +
                    "Program EndTime : " + ((Entertainment) program).getEndTime().getHour() + " : " +
                    ((Entertainment) program).getEndTime().getMinuet() + " : " +
                    ((Entertainment) program).getEndTime().getSecond() + "\n" +
                    "Reply Time : " + ((Entertainment) program).getReplayTimeStart().getHour() + " : " +
                    ((Entertainment) program).getReplayTimeStart().getMinuet() + " : " +
                    ((Entertainment) program).getReplayTimeStart().getSecond() + "\n" +
                    "Program Time : " + programTime.getHour() + " : " + programTime.getMinuet() + " : " + programTime.getSecond() +
                    "\n" + "Program Play Date : " + program.getDate().getYear() + "/" + program.getDate().getMonth() + "/" +
                    program.getDate().getDay() + "\n" + "Program Replay Date : " + ((Entertainment) program).getReplyDate().getYear() +
                    "/" + ((Entertainment) program).getReplyDate().getMonth() + "/" + ((Entertainment) program).getReplyDate().getDay() +
                    "\n" + "Program Rate : " + program.getRate());
        }
        if (program instanceof Advertisement) {
            Time programTime = program.getEndTime().timeBetweenTime(program.getStartTime());
            programInfoTextArea.setText(programInfoTextArea.getText() + "Suppprted by Company : " +
                    ((Advertisement) program).getCompany().getName() + "\n" + "Start Time : " +
                    program.getStartTime().getHour() + " : " + program.getStartTime().getMinuet() +
                    " : " + program.getStartTime().getSecond() + "\n" +
                    "Program EndTime : " + ((Advertisement) program).getEndTime().getHour() + " : " +
                    ((Advertisement) program).getEndTime().getMinuet() + " : " +
                    ((Advertisement) program).getEndTime().getSecond() + "\n" +
                    "Program Time : " + programTime.getHour() + " : " + programTime.getMinuet() + " : " + programTime.getSecond() + "\n" +
                    "Program Play Date : " + program.getDate().getYear() + "/" + program.getDate().getMonth() + "/" +
                    program.getDate().getDay() + "\n" +"Progrma Price : " + ((Advertisement) program).getPrice());
        }
        Channel channel = null;
        for (int i = 0; i < data.getChannels().size(); i++) {
            for (int j = 0; j < data.getChannels().get(i).getPrograms().size(); j++) {
                if (data.getChannels().get(i).getPrograms().get(j).getName().equals(program.getName())) {
                    channel = data.getChannels().get(i);
                    break;
                }
            }
        }
        programInfoTextArea.setText(programInfoTextArea.getText() + "\n" + "Channel : " + channel.getName());
        programInfoTextArea.setVisible(true);
        programInfoTextArea.setDisable(true);
    }

    public void showPrograms(MouseEvent mouseEvent) {
        successfulLabel.setVisible(false);
        searchLabel.setVisible(false);
        programInfoTextArea.setDisable(false);
        programInfoTextArea.setText(null);
        programInfoTextArea.setVisible(false);
        voteChoiceBox.setVisible(false);
        voteLabel.setVisible(false);
        if (channelsListView.getSelectionModel().getSelectedItem()==null){
            return;
        }
        Channel channel = null;
        try {
            channel = data.getChannelWithName(channelsListView.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
            //it never happen
            System.out.println(e.getMessage());
        }
        for (int i = programListView.getItems().size()-1; i >=0 ; i--) {
            programListView.getItems().remove(i);
        }
        for (int i = 0; i < channel.getPrograms().size(); i++) {
            programListView.getItems().add(channel.getPrograms().get(i).getName());
            System.out.println(channel.getPrograms().get(i).getName());
        }
        programListView.setVisible(true);
        programInfoTextArea.setDisable(true);
    }

    public void saveVote(MouseEvent mouseEvent) {
        searchLabel.setVisible(false);
        selectNumberLabel.setVisible(false);
        voteLabel.setVisible(false);
        try {
            data.vote(LoginPageController.activeUsername, program.getName()
                    , voteChoiceBox.getValue());
        } catch (Exception e) {
            if (voteChoiceBox.getValue() == null) {
                selectNumberLabel.setVisible(true);
                return;
            }
            if (e.getMessage().equals("USER HAS VOTED FOR THIS PROGRAM!")) {
                voteLabel.setVisible(true);
                voteButton.setVisible(false);
            }
            return;
        }
        try {
            FileOutputStream write = new FileOutputStream("ProgramData.txt");
            ObjectOutputStream writeData = new ObjectOutputStream(write);
            writeData.writeObject(data);
            writeData.flush();
            writeData.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        voteButton.setVisible(false);
    }

    public void setSaveVisible(MouseEvent mouseEvent) {
        voteButton.setVisible(true);
        successfulLabel.setVisible(false);
    }

    public void searchForProgram(MouseEvent mouseEvent) {
        back(mouseEvent);
        searchLabel.setVisible(false);
        voteLabel.setVisible(false);
        programInfoTextArea.setVisible(false);
        if (searchTextField.getText() == null) {
            return;
        }
        try {
            program = data.getProgram(searchTextField.getText());
            programInfoTextArea.setDisable(false);
            programInfoTextArea.setVisible(true);
            programInfoTextArea.setText(null);
            if (!(program instanceof Advertisement)) {
                voteChoiceBox.setVisible(true);
            }
            programInfoTextArea.setText("Program name : " + program.getName() + "\n");
            if (program instanceof Movie) {
                Time programTime = program.getEndTime().timeBetweenTime(program.getStartTime());
                programInfoTextArea.setText(programInfoTextArea.getText() + "Start Time : " +
                        program.getStartTime().getHour() + " : " + program.getStartTime().getMinuet() +
                        " : " + program.getStartTime().getSecond() + "\n" +
                        "Program EndTime : " + ((Movie) program).getEndTime().getHour() + " : " +
                        ((Movie) program).getEndTime().getMinuet() + " : " +
                        ((Movie) program).getEndTime().getSecond() + "\n" +
                        "Reply Time : " + ((Movie) program).getReplayTimeStart().getHour() + " : " +
                        ((Movie) program).getReplayTimeStart().getMinuet() + " : " +
                        ((Movie) program).getReplayTimeStart().getSecond() + "\n" +
                        "Program Time : " + programTime.getHour() + " : " + programTime.getMinuet() + " : " + programTime.getSecond()
                        + "\n" + "Program Play Date : " + program.getDate().getYear() + "/" + program.getDate().getMonth() + "/" +
                        ((Movie) program).getDate().getDay() + "\n" +
                        "Program Replay Date : " + ((Movie) program).getReplyDate().getYear() + "/" +
                        ((Movie) program).getReplyDate().getMonth() + "/" +
                        ((Movie) program).getReplyDate().getDay() + "\n" + "Program Rate : " + program.getRate());
            }
            if (program instanceof Series) {
                Time programTime = program.getEndTime().timeBetweenTime(program.getStartTime());
                programInfoTextArea.setText("Episode : " + ((Series) program).getEpisode() + "\n" +
                        "Session : " + ((Series)program).getSession()+"\n"+
                        programInfoTextArea.getText() + "Start Time : " +
                        program.getStartTime().getHour() + " : " + program.getStartTime().getMinuet() +
                        " : " + program.getStartTime().getSecond() + "\n" +
                        "Program EndTime : " + ((Series) program).getEndTime().getHour() + " : " +
                        ((Series) program).getEndTime().getMinuet() + " : " +
                        ((Series) program).getEndTime().getSecond() + "\n" +
                        "Reply Time : " + ((Series) program).getReplayTimeStart().getHour() + " : " +
                        ((Series) program).getReplayTimeStart().getMinuet() + " : " +
                        ((Series) program).getReplayTimeStart().getSecond() + "\n" +
                        "Program Time : " + programTime.getHour() + " : " + programTime.getMinuet() + " : " + programTime.getSecond()
                        + "\n" + "Program Play Date : " + program.getDate().getYear() + "/" + program.getDate().getMonth() + "/" +
                        ((Series) program).getDate().getDay() + "\n" +
                        "Program Replay Date : " + ((Series) program).getReplyDate().getYear() + "/" +
                        ((Series) program).getReplyDate().getMonth() + "/" +
                        ((Series) program).getReplyDate().getDay() + "\n" + "Program Rate : " + program.getRate());
            }
            if (program instanceof News) {
                Time programTime = program.getEndTime().timeBetweenTime(program.getStartTime());
                programInfoTextArea.setText(programInfoTextArea.getText() +
                        ((News) program).getPresenter().getName() + "\t" + ((News) program).getPresenter()
                        .getLastname() + "\n" + programInfoTextArea.getText() + "Start Time : " +
                        program.getStartTime().getHour() + " : " + program.getStartTime().getMinuet() +
                        " : " + program.getStartTime().getSecond() + "\n" +
                        "Program EndTime : " + ((News) program).getEndTime().getHour() + " : " +
                        ((News) program).getEndTime().getMinuet() + " : " +
                        ((News) program).getEndTime().getSecond() + "\n" +
                        "Reply Time : " + ((News) program).getStartTime().getHour() + " : " +
                        ((News) program).getStartTime().getMinuet() + " : " +
                        ((News) program).getStartTime().getSecond() + "\n" +
                        "Program Time : " + programTime.getHour() + " : " + programTime.getMinuet() + " : " + programTime.getSecond()+
                        "\n" + "Program Rate : " + program.getRate());
            }
            if (program instanceof Entertainment) {
                Time programTime = program.getEndTime().timeBetweenTime(program.getStartTime());
                programInfoTextArea.setText(programInfoTextArea.getText() +
                        ((Entertainment) program).getPresenter().getName() + "\t" +
                        ((Entertainment) program).getPresenter().getLastname() + "\n" + "Start Time : " +
                        program.getStartTime().getHour() + " : " + program.getStartTime().getMinuet() +
                        " : " + program.getStartTime().getSecond() + "\n" +
                        "Program EndTime : " + ((Entertainment) program).getEndTime().getHour() + " : " +
                        ((Entertainment) program).getEndTime().getMinuet() + " : " +
                        ((Entertainment) program).getEndTime().getSecond() + "\n" +
                        "Reply Time : " + ((Entertainment) program).getReplayTimeStart().getHour() + " : " +
                        ((Entertainment) program).getReplayTimeStart().getMinuet() + " : " +
                        ((Entertainment) program).getReplayTimeStart().getSecond() + "\n" +
                        "Program Time : " + programTime.getHour() + " : " + programTime.getMinuet() + " : " + programTime.getSecond() +
                        "\n" + "Program Play Date : " + program.getDate().getYear() + "/" + program.getDate().getMonth() + "/" +
                        program.getDate().getDay() + "\n" + "Program Replay Date : " + ((Entertainment) program).getReplyDate().getYear() +
                        "/" + ((Entertainment) program).getReplyDate().getMonth() + "/" + ((Entertainment) program).getReplyDate().getDay() +
                        "\n" + "Program Rate : " + program.getRate());
            }
            if (program instanceof Advertisement) {
                Time programTime = program.getEndTime().timeBetweenTime(program.getStartTime());
                programInfoTextArea.setText(programInfoTextArea.getText() + "Suppprted by Company : " +
                        ((Advertisement) program).getCompany().getName() + "\n" + "Start Time : " +
                        program.getStartTime().getHour() + " : " + program.getStartTime().getMinuet() +
                        " : " + program.getStartTime().getSecond() + "\n" +
                        "Program EndTime : " + ((Advertisement) program).getEndTime().getHour() + " : " +
                        ((Advertisement) program).getEndTime().getMinuet() + " : " +
                        ((Advertisement) program).getEndTime().getSecond() + "\n" +
                        "Program Time : " + programTime.getHour() + " : " + programTime.getMinuet() + " : " + programTime.getSecond() + "\n" +
                        "Program Play Date : " + program.getDate().getYear() + "/" + program.getDate().getMonth() + "/" +
                        program.getDate().getDay() + "\n" +"Progrma Price : " + ((Advertisement) program).getPrice());
            }
            Channel channel = null;
            for (int i = 0; i < data.getChannels().size(); i++) {
                for (int j = 0; j < data.getChannels().get(i).getPrograms().size(); j++) {
                    if (data.getChannels().get(i).getPrograms().get(j).getName().equals(program.getName())) {
                        channel = data.getChannels().get(i);
                        break;
                    }
                }
            }
            programInfoTextArea.setText(programInfoTextArea.getText() + "\n" + "Channel : " + channel.getName());
            programInfoTextArea.setVisible(true);
            programInfoTextArea.setDisable(true);
            voteChoiceBox.setVisible(true);
            voteLabel.setVisible(false);
            successfulLabel.setVisible(false);
        } catch (Exception e) {
            if (e.getMessage().equals("NO SUCH PROGRAM WITH THIS NAME!")) {
                searchLabel.setVisible(true);
                searchTextField.setText(null);
            }
        }
        searchTextField.setText(null);
    }

    public void logOut(MouseEvent mouseEvent) {
        LoginPageController.activeUsername = null;
        try {
            new PageLoader().load("/View/LoginPage.fxml");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void logOutEnter(MouseEvent mouseEvent) {
        logOutButton.setUnderline(true);
    }

    public void logOutExit(MouseEvent mouseEvent) {
        logOutButton.setUnderline(false);
    }

    public void add(MouseEvent mouseEvent) {
        if (addChoiceBox.getValue() == null) return;
        try {
            switch (addChoiceBox.getValue()) {
                case "Program":
                    new PageLoader().load("/View/AddProgram.fxml")
                    ;
                    break;
                case "Presenter":
                    new PageLoader().load("/View/AddPresenter.fxml")
                    ;
                    break;
                case "Request":
                    new PageLoader().load("/View/RequestStatusPage.fxml");
                    ;
                    break;
                case "Channel":
                    new PageLoader().load("/View/AddChannel.fxml")
                    ;
                    break;
                case "Formula":
                    new PageLoader().load("/View/Formula.fxml")
                    ;
                    break;
                case "Manager":
                    new PageLoader().load("/View/AddManager.fxml")
                    ;
                    break;
                case "Company":
                    new PageLoader().load("/View/AddCompany.fxml")
                    ;
                    break;
                case "Delete":
                    new PageLoader().load("/View/DeleteProgrm.fxml");
                    ;
                    break;
            }
        } catch (Exception e) {
            //it never happen
            System.out.println(e.getMessage());
        }
    }
}
