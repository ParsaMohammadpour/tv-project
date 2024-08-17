package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import Model.Data.Data;
import Model.Program.Advertisement.Company;
import Model.MainPage.*;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;

public class AddCompanyController {
    private Data data;

    @FXML
    ListView<String>companiesListView;

    @FXML
    TextField moneyTextField;

    @FXML
    Button addButton;

    @FXML
    TextField nameTextField;

    @FXML
    TextField passwordTextField;

    @FXML
    TextField priceTextField;

    @FXML
    Button makeButton;

    @FXML
    Button backButton;

    @FXML
    Label nameLabel;

    @FXML
    Label passwordLabel;

    @FXML
    Label priceLabel;

    @FXML
    Label addLabel;

    @FXML
    Label done1Label;

    @FXML
    Label done2Label;

    public void initialize(){
        data=LoginPageController.getData();
        for (int i = 0; i < data.getCompanies().size(); i++) {
            companiesListView.getItems().add(data.getCompanies().get(i).getName());
        }
        moneyTextField.setText(null);
        nameTextField.setText(null);
        passwordTextField.setText(null);
        priceTextField.setText(null);
    }


    public void addEnter(MouseEvent mouseEvent) {
        addButton.setUnderline(true);
    }

    public void addExit(MouseEvent mouseEvent) {
        addButton.setUnderline(false);
    }

    public void makeEnter(MouseEvent mouseEvent) {
        makeButton.setUnderline(true);
    }

    public void makeExit(MouseEvent mouseEvent) {
        makeButton.setUnderline(false);
    }

    public void backEnter(MouseEvent mouseEvent) {
        backButton.setUnderline(true);
    }

    public void backExit(MouseEvent mouseEvent) {
        backButton.setUnderline(false);
    }

    public void add(MouseEvent mouseEvent) {
        addLabel.setVisible(false);
        done1Label.setVisible(false);
        if (companiesListView.getSelectionModel().getSelectedItem()==null||moneyTextField.getText()==null){
            addLabel.setVisible(true);
            return;
        }
        try(FileOutputStream write=new FileOutputStream(Paths.get("ProgramData.txt").toFile());
        ObjectOutputStream writeData=new ObjectOutputStream(write)) {
            for (int i = 0; i < data.getCompanies().size(); i++) {
                if (data.getCompanies().get(i).getName().equals(companiesListView.getSelectionModel().getSelectedItem())){
                    data.getCompanies().get(i).addMoney(Long.parseLong(moneyTextField.getText()));
                    done1Label.setVisible(true);
                    System.out.println(data.getCompanies().get(i).getMoney());
                    break;
                }
            }
            writeData.writeObject(data);
        }catch (Exception e){
            addLabel.setVisible(true);
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

    public void make(MouseEvent mouseEvent) {
        nameLabel.setVisible(false);
        passwordLabel.setVisible(false);
        priceLabel.setVisible(false);
        done2Label.setVisible(false);
        if (nameTextField.getText()==null){
            nameLabel.setVisible(true);
            return;
        }
        if (passwordTextField.getText()==null){
            passwordLabel.setVisible(true);
            return;
        }
        if (priceTextField.getText()==null){
            priceLabel.setVisible(true);
            return;
        }
        try {
            for (int i = 0; i < data.getCompanies().size(); i++) {
                if (data.getCompanies().get(i).getName().equals(nameTextField.getText())){
                    nameLabel.setVisible(true);
                    return;
                }
            }
            if (passwordTextField.getText().length()<5){
                passwordLabel.setVisible(true);
                return;
            }
            Company company=new Company(nameTextField.getText(),Long.parseLong
                    (priceTextField.getText()),passwordTextField.getText());
            data.getCompanies().add(company);
            companiesListView.getItems().add(nameTextField.getText());
            done2Label.setVisible(true);
        }catch (Exception e){
            priceLabel.setVisible(true);
            System.out.println(e.getMessage());
        }
        try (FileOutputStream write=new FileOutputStream(Paths.get("ProgramData.txt").toFile());
             ObjectOutputStream writeData=new ObjectOutputStream(write)){
            writeData.writeObject(data);
            writeData.flush();
        }catch (Exception e){
            //it never happen
            System.out.println(e.getMessage());
        }
    }
}