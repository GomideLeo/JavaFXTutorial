package ch.makery.address;

import ch.makery.address.model.manager.FileManager;
import ch.makery.address.model.manager.StageManager;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.image.Image;


public class MainApp extends Application {

    private StageManager stageManager;
    private FileManager fileManager;
    
    public MainApp() {
        stageManager = StageManager.getInstance();
        fileManager = FileManager.getInstance();
    }
    
    @Override
    public void start(Stage primaryStage) {
        stageManager.setPrimaryStage(primaryStage);
        stageManager.setStageTitle(primaryStage, "AddressApp");

        // Set the application icon.
        stageManager.setStageIcon(new Image("file:resources/images/address_book_icon.png"));

        stageManager.initRootLayout();

        stageManager.showPersonOverview();
    }

    public static void main(String[] args) {
        launch(args);
    }
  
}