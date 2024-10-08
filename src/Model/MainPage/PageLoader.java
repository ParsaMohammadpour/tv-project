package Model.MainPage;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;

public class PageLoader {
    static final int HEIGHT =700;

    static final int WIDTH =700;
    public static Stage stage;

    public static void initStage(Stage primaryStage){
        stage =primaryStage;
        stage.setTitle("Seda Sima");
        stage.getIcons().add(new Image(Paths.get("Image/Sedasima.png").toUri().toString()));
        stage.setHeight(HEIGHT);
        stage.setWidth(WIDTH);
    }

    public void load(String url)throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource(url));
        stage.setScene(new Scene(root,HEIGHT,WIDTH));
        stage.show();
    }
}
