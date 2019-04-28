package ch.makery.address;

import ch.makery.address.manager.FileManager;
import ch.makery.address.manager.StageManager;
import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;


public class MainApp extends Application {

    private StageManager stageManager;
    private FileManager fileManager;
    
    public MainApp() {
        stageManager = StageManager.getInstance();
        fileManager = FileManager.getInstance();
    }

    public void initRootLayout() {
        try {
            // Carrega o root layout do arquivo fxml.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            stageManager.setRootLayout ((BorderPane) loader.load());

            // Mostra a scene (cena) contendo o root layout.
            Scene scene = new Scene(stageManager.getRootLayout());
            stageManager.primaryStageSetScene(scene);

            stageManager.showPrimaryStage();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Tenta carregar o último arquivo de pessoa aberto.
        File file = fileManager.getPersonFilePath();
        if (file != null) {
            fileManager.loadPersonDataFromFile(file);
        }
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