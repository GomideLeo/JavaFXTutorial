package ch.makery.address.view;

import ch.makery.address.model.dao.PersonDAO;
import java.io.File;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import ch.makery.address.model.manager.FileManager;
import ch.makery.address.model.manager.PersonDataManager;
import ch.makery.address.model.manager.StageManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * O controlador para o root layout. O root layout provê um layout básico
 * para a aplicação contendo uma barra de menu e um espaço onde outros elementos
 * JavaFX podem ser colocados.
 * 
 * @author Marco Jakob
 */
public class RootLayoutController {

    private PersonDataManager personDataManager;
    private StageManager stageManager;
    private FileManager fileManager;
    private PersonDAO personDAO;


    public RootLayoutController(){
        personDataManager = PersonDataManager.getInstance();
        stageManager = StageManager.getInstance();
        fileManager = FileManager.getInstance();
        personDAO = PersonDAO.getInstance();
    }
    
    @FXML
    private void handleNew() {
        try {
            personDataManager.clearPersonData();
            personDAO.removeAll();
        } catch (Exception ex) {
            Logger.getLogger(RootLayoutController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        
        File file = fileChooser.showOpenDialog(stageManager.getPrimaryStage());

        if (file != null) {
            fileManager.loadPersonDataFromFile(file);
        }
    }

    @FXML
    private void handleSave() {
        File personFile = fileManager.getPersonFilePath();
        if (personFile != null) {
            fileManager.savePersonDataToFile(personFile);
        } else {
            handleSaveAs();
        }
    }

    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(stageManager.getPrimaryStage());

        if (file != null) {
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            fileManager.savePersonDataToFile(file);
        }
    }

    @FXML
    private void handleAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("AddressApp");
        alert.setHeaderText("Sobre");
        alert.setContentText("Autor: Marco Jakob\nWebsite: http://code.makery.ch");
        alert.showAndWait();
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }
    
    @FXML
    private void handleShowBirthdayStatistics() {
        stageManager.showBirthdayStatistics();
    }
}