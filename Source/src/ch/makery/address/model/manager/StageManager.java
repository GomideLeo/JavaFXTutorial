/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.makery.address.model.manager;

import ch.makery.address.MainApp;
import ch.makery.address.model.Person;
import ch.makery.address.view.BirthdayStatisticsController;
import ch.makery.address.view.PersonEditDialogController;
import ch.makery.address.view.RootLayoutController;
import java.io.File;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author leocg
 */
public class StageManager {
    
    private static final StageManager instance = new StageManager();
    private PersonDataManager personDataManager;
    private FileManager fileManager;
    private Stage primaryStage;
    private BorderPane rootLayout;
    
    private StageManager(){
        personDataManager = PersonDataManager.getInstance();
        fileManager = FileManager.getInstance();
    }
    
    public static StageManager getInstance(){
        return instance;
    }
    
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    public void primaryStageSetScene(Scene scene){
        primaryStage.setScene(scene);
    }
    
    public void showPrimaryStage(){
        primaryStage.show();
    }
    
    public void setRootLayout(BorderPane rootLayout) {
        this.rootLayout = rootLayout;
    }
    
    public void setPrimaryStage (Stage primaryStage){
        this.primaryStage = primaryStage;
    }
    
    public BorderPane getRootLayout() {
        return rootLayout;
    }
    
    public void setStageTitle (Stage stage, String title) {
        stage.setTitle(title);
    }
    
    public void setPrimaryStageTitle (String title) {
        primaryStage.setTitle(title);
    }
    
    public void setStageIcon (Image icon){
        this.primaryStage.getIcons().add(icon);
    }
    
    public void showPersonOverview() {
        try {
            // Carrega a person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Define a person overview no centro do root layout.
            rootLayout.setCenter(personOverview);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void initRootLayout() {
        try {
            // Carrega o root layout do arquivo fxml.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Mostra a scene (cena) contendo o root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            // Dá ao controller o acesso ao main app.
            RootLayoutController controller = loader.getController();

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Tenta carregar o último arquivo de pessoa aberto.
        File file = fileManager.getPersonFilePath();
        if (file != null) {
            fileManager.loadPersonDataFromFile(file);
        }
    }
    
    public boolean showPersonEditDialog(Person person, boolean isNewPerson) {
        try {
            // Carrega o arquivo fxml e cria um novo stage para a janela popup.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Cria o palco dialogStage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Define a pessoa no controller.
            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person, isNewPerson);

            // Mostra a janela e espera até o usuário fechar.
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void showBirthdayStatistics() {
        try {
            // Carrega o arquivo fxml e cria um novo palco para o popup.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/BirthdayStatistics.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Birthday Statistics");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Define a pessoa dentro do controller.
            BirthdayStatisticsController controller = loader.getController();
            controller.setPersonData(personDataManager.getPersonData());

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
