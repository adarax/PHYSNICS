package edu.vanier.physnics;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import edu.vanier.physnics.mainmenu.MainMenuController;
// import edu.vanier.physnics.stackedblock.BlockFrontEndController;   
import edu.vanier.physnics.conservation.ConservationController;
// import edu.vanier.physnics.projectilemotion.ProjectileController;

public class App extends Application {
    public Stage stage;

    @Override
    public void start(Stage stage) throws Exception
    {    
        this.stage = stage;
        /*
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainmenu.fxml"));
        MainMenuController mwc = new MainMenuController(this.stage);
        loader.setController(mwc);
        */
        
        // ProjectileController PCC = new ProjectileController();
        // loader.setController(PCC);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/conservation.fxml"));
        ConservationController mwc = new ConservationController();
        loader.setController(mwc);
        
        
        Parent root = loader.load();               
        Scene scene = new Scene(root, 1920, 1080);
        

        this.stage.setScene(scene);        
        this.stage.setMaximized(true);
        this.stage.sizeToScene();
        this.stage.setFullScreen(true);
        this.stage.getIcons().add(new Image("/images/app_icon.png"));
       
        this.stage.show();
        
        
        
        

    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public static void returnToMainMenu(){
        
    }
}
