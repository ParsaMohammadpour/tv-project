package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import Model.Channel.Channel;
import Model.Channel.Formula;
import Model.Data.Data;
import Model.Time.Time;
import Model.MainPage.*;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;

public class FormulaController {
    private Data data;

    private boolean isGeneralManager=false;

    @FXML
    Button backButton;

    @FXML
    Button doneButton;

    @FXML
    TextField preFactorTextField;

    @FXML
    TextField powTextField;

    @FXML
    TextField goldTimeTextField;

    @FXML
    TextField betweenProgramTextField;

    @FXML
    TextField s_Hour;

    @FXML
    TextField s_Minuet;

    @FXML
    TextField s_Second;

    @FXML
    TextField e_Hour;

    @FXML
    TextField e_Minuet;

    @FXML
    TextField e_Second;

    @FXML
    Label timeLabel;

    @FXML
    Label channelLabel;

    @FXML
    ListView<String>channelListView;

    public void initialize(){
        data=LoginPageController.getData();
        if (LoginPageController.activeUsername.equals(data.getGeneralManager().getUsername()))isGeneralManager=true;
        for (int i = 0; i < data.getChannels().size(); i++) {
            channelListView.getItems().add(data.getChannels().get(i).getName());
        }
        if (isGeneralManager)channelListView.setVisible(true);
    }

    public void back(MouseEvent mouseEvent) {
        try {
            new PageLoader().load("/View/Page.fxml");
        }catch (Exception e){
            //it never happen
            System.out.println(e.getMessage());
        }
    }

    public void done(MouseEvent mouseEvent) {
        channelLabel.setVisible(false);
        timeLabel.setVisible(false);
        try {
            timeLabel.setVisible(true);
            Time time1=new Time(Integer.parseInt(s_Hour.getText()),
                    Integer.parseInt(s_Minuet.getText()),Integer.parseInt(s_Second.getText()));
            Time time2=new Time(Integer.parseInt(e_Hour.getText()),
                    Integer.parseInt(e_Minuet.getText()),Integer.parseInt(e_Second.getText()));
            timeLabel.setVisible(false);
            int preFactorOfTime=Integer.parseInt(preFactorTextField.getText());
            int pow=Integer.parseInt(powTextField.getText());
            int goldTimeFactor=Integer.parseInt(goldTimeTextField.getText());
            int betweenProgram=1;
            if (betweenProgramTextField.getText()!=null) {
                betweenProgram = Integer.parseInt(betweenProgramTextField.getText());
            }
            Formula formula = new Formula(preFactorOfTime, pow, time1, time2, goldTimeFactor, betweenProgram);
            if (isGeneralManager){
                if (channelListView.getSelectionModel().getSelectedItem()==null){
                    channelLabel.setVisible(true);
                    return;
                }else {
                    data.getChannelWithName(channelListView.getSelectionModel().getSelectedItem()).setFormula(formula);
                }
            }else {
                Channel channel=data.getManagerWithUsername(LoginPageController.activeUsername).getChannel();
                channel.setFormula(formula);
            }
        }catch (Exception e){
            //it never happen
            System.out.println(e.getMessage());
        }
        try (FileOutputStream write =new FileOutputStream(Paths.get("ProgramData.txt").toFile());
             ObjectOutputStream writeData=new ObjectOutputStream(write)){
            writeData.writeObject(data);
            writeData.flush();
        }catch (Exception e){
            //it never happen
            System.out.println(e.getMessage());
        }
        System.out.println("FORMULA MADE SUCCESFULLY");
    }
}
