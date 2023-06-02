package edu.vanier.physnics;

import edu.vanier.physnics.mainmenu.MainMenuController;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

/**
 * Class that handles the startup of the application.
 * 
 * @author Newton's Champions
 */
public class App extends Application {
    private Stage mainMenu;

    /**
     * Starts the application and loads the main menu.
     * 
     * @param stage the stage on which the Scene is drawn
     * @throws IOException occurs if the Scene cannot be loaded
     */
    @Override
    public void start(Stage stage) throws IOException
    {    
        mainMenu = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainmenu.fxml"));
        MainMenuController mainMenuController = new MainMenuController(mainMenu);
        loader.setController(mainMenuController);
        
        try
        {
            Parent root = loader.load();
            Scene scene = new Scene(root, 1920, 1080);
            mainMenu.setScene(scene);
        } catch (IOException ex) /// abbreviated variable name
        {
            System.out.println("Something went wrong loading the application");
        }
        
        mainMenu.setMaximized(true);
        mainMenu.sizeToScene();
        mainMenu.getIcons().add(new Image("/images/app_icon.png"));
        mainMenu.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        mainMenu.setFullScreenExitHint("");
        mainMenu.setFullScreen(true);
        mainMenu.show();
    }
    
    /**
     * Takes an array of Strings (arguments) and launches the application.
     * 
     * @param args an array of String arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
