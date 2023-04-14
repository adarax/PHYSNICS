package edu.vanier.physnics;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import edu.vanier.physnics.mainmenu.MainMenuController;
// import edu.vanier.physnics.stackedblock.BlockFrontEndController;   
// import edu.vanier.physnics.conservation.ConservationController;
// import edu.vanier.physnics.projectilemotion.ProjectileController;

public class App extends Application {
    public Stage mainMenu;

    @Override
    public void start(Stage stage) throws Exception
    {    
        mainMenu = stage;
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainmenu.fxml"));
        MainMenuController mwc = new MainMenuController(mainMenu);
        loader.setController(mwc);
        
        // ProjectileController PCC = new ProjectileController();
        // loader.setController(PCC);
        
        Parent root = loader.load();               
        Scene scene = new Scene(root, 1920, 1080);
        

        mainMenu.setScene(scene);        
        mainMenu.setMaximized(true);
        mainMenu.sizeToScene();
        mainMenu.setFullScreen(true);
       
        mainMenu.show();
        
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.sizeToScene();
        stage.getIcons().add(new Image("/images/app_icon.png"));
        stage.setFullScreen(true);
        stage.show();

    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
