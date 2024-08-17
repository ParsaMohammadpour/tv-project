package Model.MainPage;

import javafx.application.Application;

import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        PageLoader.initStage(primaryStage);
        new PageLoader().load("/View/LoginPage.fxml");
    }

    public static void main(String[] args) {
        launch(args);
    }
}