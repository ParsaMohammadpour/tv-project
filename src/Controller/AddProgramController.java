package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import Model.Data.Data;
import Model.Person.Manager;
import Model.Person.Presenter;
import Model.Program.*;
import Model.Program.Advertisement.Advertisement;
import Model.Program.Advertisement.Company;
import Model.Time.Date;
import Model.Time.Time;
import Model.MainPage.*;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;

public class AddProgramController {
    private Data data;

    private boolean isGeneralManager;

    @FXML
    ChoiceBox<String> add_EditChoiceBox;

    @FXML
    Button setItemButton;

    @FXML
    ChoiceBox<String> itemTypeChoiceBox;

    @FXML
    ChoiceBox<String> companiesChoiceBox;

    @FXML
    ChoiceBox<String> presenterChoiceBox;

    @FXML
    ChoiceBox<String> channelChoiceBox;

    @FXML
    TextField programNameTextField;

    @FXML
    TextField s_Hour;

    @FXML
    TextField s_Minuet;

    @FXML
    TextField s_Second;

    @FXML
    TextField f_Hour;

    @FXML
    TextField f_Minuet;

    @FXML
    TextField f_Second;

    @FXML
    TextField p_Year;

    @FXML
    TextField p_Month;

    @FXML
    TextField p_Day;

    @FXML
    TextField r_Year;

    @FXML
    TextField r_Month;

    @FXML
    TextField r_Day;

    @FXML
    TextField episodeTextField;

    @FXML
    Label programNameLabel;

    @FXML
    Label s_TimeLabel;

    @FXML
    Label f_TimeLabel;

    @FXML
    Label p_DateLabel;

    @FXML
    Label r_DateLabel;

    @FXML
    Label timeErrorLabel;

    @FXML
    Label dateErrorLabel;

    @FXML
    Label nullLabel;

    @FXML
    Label episodeLabel;

    @FXML
    Button makeProgramButton;

    @FXML
    Label nameErrorLabel;

    @FXML
    TextField sessionTextField;

    @FXML
    TextField replayStartTimeHour;

    @FXML
    TextField replayStartTimeMinuet;

    @FXML
    TextField replayStartTimeSecond;

    @FXML
    Label replayStartTimeLabel;

    @FXML
    Button backToMainButton;

    public void initialize() {
        backToMainButton.setVisible(true);
        data = LoginPageController.getData();
        if (LoginPageController.activeUsername.equals(data.getGeneralManager().getUsername())) {
            isGeneralManager = true;
        }
        sessionTextField.setVisible(false);
        nameErrorLabel.setVisible(false);
        add_EditChoiceBox.getItems().add("Edit");
        add_EditChoiceBox.getItems().add("Add");
        itemTypeChoiceBox.getItems().add("Movie");
        itemTypeChoiceBox.getItems().add("Series");
        itemTypeChoiceBox.getItems().add("News");
        itemTypeChoiceBox.getItems().add("Entertainment");
        for (int i = 0; i < data.getChannels().size(); i++) {
            channelChoiceBox.getItems().add(data.getChannels().get(i).getName());
        }
        for (int i = 0; i < data.getPresenters().size(); i++) {
            presenterChoiceBox.getItems().add(data.getPresenters().get(i).getName());
        }
        for (int i = 0; i < data.getCompanies().size(); i++) {
            companiesChoiceBox.getItems().add(data.getCompanies().get(i).getName());
        }
        add_EditChoiceBox.setVisible(true);
        itemTypeChoiceBox.setVisible(true);
        makeProgramButton.setVisible(false);
        channelChoiceBox.setVisible(false);
        setItemButton.setVisible(true);
        programNameLabel.setVisible(false);
        programNameTextField.setVisible(false);
        nullLabel.setVisible(false);
        dateErrorLabel.setVisible(false);
        episodeLabel.setVisible(false);
        f_TimeLabel.setVisible(false);
        p_DateLabel.setVisible(false);
        r_DateLabel.setVisible(false);
        s_TimeLabel.setVisible(false);
        timeErrorLabel.setVisible(false);
        companiesChoiceBox.setVisible(false);
        presenterChoiceBox.setVisible(false);
        episodeTextField.setVisible(false);
        f_TimeSetVisibility(false);
        r_DatesetVisibility(false);
        p_DateSetVisibility(false);
        s_TimeSetVisibility(false);
        replayStartTimeSetVisibility(false);
    }

    private void f_TimeSetVisibility(boolean visibility) {
        f_Hour.setVisible(visibility);
        f_Minuet.setVisible(visibility);
        f_Second.setVisible(visibility);
        f_TimeLabel.setVisible(visibility);
    }

    private void s_TimeSetVisibility(boolean visibility) {
        s_Hour.setVisible(visibility);
        s_Minuet.setVisible(visibility);
        s_Second.setVisible(visibility);
        s_TimeLabel.setVisible(visibility);
    }

    private void p_DateSetVisibility(boolean visibility) {
        p_Day.setVisible(visibility);
        p_Month.setVisible(visibility);
        p_Year.setVisible(visibility);
        p_DateLabel.setVisible(visibility);
    }

    private void r_DatesetVisibility(boolean visibility) {
        r_Day.setVisible(visibility);
        r_Month.setVisible(visibility);
        r_Year.setVisible(visibility);
        r_DateLabel.setVisible(visibility);
    }

    private void replayStartTimeSetVisibility(boolean visibility) {
        replayStartTimeHour.setVisible(visibility);
        replayStartTimeMinuet.setVisible(visibility);
        replayStartTimeSecond.setVisible(visibility);
        replayStartTimeLabel.setVisible(visibility);
    }

    public void setItems(MouseEvent mouseEvent) {
        if (itemTypeChoiceBox.getValue() == null || add_EditChoiceBox.getValue() == null) return;
        if (isGeneralManager) channelChoiceBox.setVisible(true);
        if (itemTypeChoiceBox.getValue().equals("Edit")){
            makeProgramButton.setText("Edit");
        }else {
            makeProgramButton.setText("Make");
        }
        episodeTextField.setVisible(false);
        presenterChoiceBox.setVisible(false);
        companiesChoiceBox.setVisible(false);
        r_DatesetVisibility(true);
        p_DateSetVisibility(true);
        s_TimeSetVisibility(true);
        f_TimeSetVisibility(true);
        replayStartTimeSetVisibility(true);
        switch (itemTypeChoiceBox.getValue()) {
            case "Movie":
                break;
            case "Series": {
                episodeTextField.setVisible(true);
                sessionTextField.setVisible(true);
            }
            break;
            case "News": {
                r_DatesetVisibility(false);
                replayStartTimeSetVisibility(false);
            }
            case "Entertainment":
                presenterChoiceBox.setVisible(true);
                break;
        }
        makeProgramButton.setVisible(true);
        programNameTextField.setVisible(true);
        programNameLabel.setVisible(true);
    }

    public void setItemButtonEnter(MouseEvent mouseEvent) {
        setItemButton.setUnderline(true);
    }

    public void setItemButtonExit(MouseEvent mouseEvent) {
        setItemButton.setUnderline(false);
    }

    public void make_EditProgram(MouseEvent mouseEvent) {
        timeErrorLabel.setVisible(false);
        episodeLabel.setVisible(false);
        nullLabel.setVisible(false);
        dateErrorLabel.setVisible(false);
        nameErrorLabel.setVisible(false);
        if (add_EditChoiceBox.getValue().equals("Add")) {
            try {
                Program program = null;
                Date date1 = new Date(Integer.parseInt(p_Year.getText()), Integer.parseInt(p_Month.getText()), Integer.parseInt
                        (p_Day.getText()));
                Date date2=null;
                Time time3=null;
                if ((!itemTypeChoiceBox.getValue().equals("News"))&&(!itemTypeChoiceBox.getValue().equals("Series"))){
                    date2 = new Date(Integer.parseInt(r_Year.getText()), Integer.parseInt(r_Month.getText()), Integer.parseInt
                        (r_Day.getText()));
                    time3 = new Time(Integer.parseInt(replayStartTimeHour.getText()), Integer.parseInt(replayStartTimeMinuet
                        .getText()), Integer.parseInt(replayStartTimeSecond.getText()));
                }
                Time time1 = new Time(Integer.parseInt(s_Hour.getText()), Integer.parseInt(s_Minuet.getText()), Integer.parseInt
                        (s_Second.getText()));
                Time time2 = new Time(Integer.parseInt(f_Hour.getText()), Integer.parseInt(f_Minuet.getText()), Integer.parseInt
                        (f_Second.getText()));
                if (itemTypeChoiceBox.getValue().equals("Movie")) {
                    program = new Movie(programNameTextField.getText(), time1, time2, time3, date1, date2);
                }
                if (itemTypeChoiceBox.getValue().equals("Series")) {
                    program = new Series(programNameTextField.getText(), time1, time2, time3, Integer.parseInt
                            (sessionTextField.getText()), Integer.parseInt(episodeTextField.getText()), date1, date2);
                }
                if (itemTypeChoiceBox.getValue().equals("Entertainment")) {
                    Presenter presenter = data.getPresenterWithName(presenterChoiceBox.getValue());
                    program = new Entertainment(programNameTextField.getText(), time1, time2, time3, presenter, date1, date2);
                }
                if (itemTypeChoiceBox.getValue().equals("News")) {
                    Presenter presenter = data.getPresenterWithName(presenterChoiceBox.getValue());
                    program = new News(programNameTextField.getText(), time1, time2, presenter);
                }
                if (!isGeneralManager) {
                    System.out.println("Correct if");
                    data.addProgram(program, LoginPageController.activeUsername);
                    System.out.println("TAMAM");
                } else {
                    System.out.println("********************************");
                    data.addProgram(program, channelChoiceBox.getValue(), isGeneralManager);
                }
            } catch (Exception e) {
                if (e.getMessage().equals("THIS NAME HAS BEEN USED!")) {
                    nameErrorLabel.setVisible(true);
                    return;
                }
                if (e.getMessage().equals("HAVE TIME CRASH!")) {
                    timeErrorLabel.setVisible(true);
                    return;
                }
                System.out.println(e.getMessage());
            }
        } else {
            try {
                Program program = data.getProgram(programNameTextField.getText());
                if (!isGeneralManager){
                    boolean hasProgramInManagerChannel=false;
                    for (int i = 0; i < data.getManagerWithUsername(LoginPageController.activeUsername)
                            .getChannel().getPrograms().size(); i++) {
                        if (data.getManagerWithUsername(LoginPageController.activeUsername).getChannel()
                                .getPrograms().get(i).getName().equals(programNameTextField.getText())){
                            hasProgramInManagerChannel=true;
                            break;
                        }
                    }
                    if (!hasProgramInManagerChannel){
                        nameErrorLabel.setVisible(true);
                        return;
                    }
                }
                Date date1 = new Date(Integer.parseInt(p_Year.getText()), Integer.parseInt(p_Month.getText()), Integer.parseInt
                        (p_Day.getText()));
                Date date2 = new Date(Integer.parseInt(r_Year.getText()), Integer.parseInt(r_Month.getText()), Integer.parseInt
                        (r_Day.getText()));
                Time time1 = new Time(Integer.parseInt(s_Hour.getText()), Integer.parseInt(s_Minuet.getText()), Integer.parseInt
                        (s_Second.getText()));
                Time time2 = new Time(Integer.parseInt(f_Hour.getText()), Integer.parseInt(f_Minuet.getText()), Integer.parseInt
                        (f_Second.getText()));
                Time time3 = new Time(Integer.parseInt(replayStartTimeHour.getText()), Integer.parseInt(replayStartTimeMinuet
                        .getText()), Integer.parseInt(replayStartTimeSecond.getText()));
                if (!isGeneralManager) {
                    Manager manager = data.getManagerWithUsername(LoginPageController.activeUsername);
                    Program program1 = new Program(programNameTextField.getText(), time1, time2, date1, date2);
                    for (int i = 0; i < manager.getChannel().getPrograms().size(); i++) {
                        if ((itemTypeChoiceBox.getValue().equals("Series")) || (manager.getChannel().getPrograms().get(i)
                                instanceof Series)) {
                            if (program1.getStartTime().isLater(manager.getChannel().getPrograms().get(i).getEndTime()) ||
                                    manager.getChannel().getPrograms().get(i).getStartTime().isLater(program1.getEndTime())) {
                                continue;
                            } else {
                                throw new Exception("HAVE TIME CRASH!");
                            }
                        }
                        if (program1.getDate().equals(manager.getChannel().getPrograms().get(i).getDate())) {
                            if (program1.hasTimeCrash(manager.getChannel().getPrograms().get(i))) {
                                throw new Exception("HAVE TIME CRASH!");
                            }
                        }
                    }
                } else {
                    if (itemTypeChoiceBox.getValue().equals("Movie")) {
                        program.setDate(date1);
                        program.setReplyDate(date2);
                        program.setStartTime(time1);
                        program.setEndTime(time2);
                        program.setReplayTimeStart(time3);
                    }
                    if (itemTypeChoiceBox.getValue().equals("Series")) {
                        program.setStartTime(time1);
                        program.setEndTime(time2);
                        program.setReplayTimeStart(time3);
                        program.setDate(date1);
                        program.setReplyDate(date2);
                        if (program instanceof Series) {
                            //always happen
                            ((Series) program).setSession(Integer.parseInt(sessionTextField.getText()));
                            ((Series) program).setEpisode(Integer.parseInt(episodeTextField.getText()));
                        }
                    }
                    if (itemTypeChoiceBox.getValue().equals("Entertainment")) {
                        Presenter presenter = data.getPresenterWithName(presenterChoiceBox.getValue());
                        program.setStartTime(time1);
                        program.setEndTime(time2);
                        program.setReplayTimeStart(time3);
                        program.setDate(date1);
                        program.setReplyDate(date2);
                        ((Entertainment) program).setPresenter(presenter);
                    }
                    if (itemTypeChoiceBox.getValue().equals("News")) {
                        Presenter presenter = data.getPresenterWithName(presenterChoiceBox.getValue());
                        program.setStartTime(time1);
                        program.setEndTime(time2);
                        program.setReplayTimeStart(time3);
                        program.setDate(date1);
                        program.setReplyDate(date2);
                        ((News) program).setPresenter(presenter);
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
                if (e.getMessage().equals("NO SUCH PROGRAM WITH THIS NAME!")){
                    nameErrorLabel.setVisible(true);
                    return;
                }
                if (e.getMessage().equals("NO SUCH PROGRAM!")) {
                    nameErrorLabel.setVisible(true);
                    return;
                }
                if (e.getMessage().equals("THIS NAME HAS BEEN USED!")) {
                    nameErrorLabel.setVisible(true);
                    return;
                }
                if (e.getMessage().equals("HAVE TIME CRASH!")) {
                    timeErrorLabel.setVisible(true);
                    return;
                }
                if (e.getMessage().equals("NO SUCH PRESENTER")) {
                    //it never happen
                    System.out.println(e.getMessage());
                }
            }
        }
        try (FileOutputStream write = new FileOutputStream(Paths.get("ProgramData.txt").toFile());
             ObjectOutputStream writeData = new ObjectOutputStream(write)) {
            writeData.writeObject(data);
            writeData.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void makeButtonEnter(MouseEvent mouseEvent) {
        makeProgramButton.setUnderline(true);
    }

    public void makeButtonExit(MouseEvent mouseEvent) {
        makeProgramButton.setUnderline(false);
    }

    public void backToMainPage(MouseEvent mouseEvent) {
        try {
            new PageLoader().load("/View/Page.fxml");
        } catch (Exception e) {
            //it never happen
            System.out.println(e.getMessage());
        }
    }

    public void backEnter(MouseEvent mouseEvent) {
        backToMainButton.setUnderline(true);
    }

    public void backExit(MouseEvent mouseEvent) {
        backToMainButton.setUnderline(false);
    }
}