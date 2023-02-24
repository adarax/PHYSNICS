package edu.vanier.physnics;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import edu.vanier.physnics.mainmenu.MainMenuController;

public class App extends Application {
    public Stage mainMenu;

    @Override
    public void start(Stage stage) throws Exception {
        mainMenu = stage;
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainmenu.fxml"));

        MainMenuController mwc = new MainMenuController();
        loader.setController(mwc);
        
        Parent root = loader.load();               
        Scene scene = new Scene(root, 600, 400);
        
        stage.setScene(scene);
        stage.setResizable(true);
        stage.sizeToScene();
        stage.getIcons().add(new Image("/images/app_icon.png"));
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
