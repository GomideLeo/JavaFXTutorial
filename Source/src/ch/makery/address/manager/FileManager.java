/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.makery.address.manager;

import ch.makery.address.MainApp;
import ch.makery.address.model.PersonListWrapper;
import java.io.File;
import java.util.prefs.Preferences;
import javafx.scene.control.Alert;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author leocg
 */
public class FileManager {
    
    private static final FileManager instance = new FileManager();
    private PersonDataManager personDataManager;
    private StageManager stageManager;
    
    private FileManager(){
        personDataManager = PersonDataManager.getInstance();
        stageManager = StageManager.getInstance();
    }
    
    public static FileManager getInstance(){
        return instance;
    }
    
    public File getPersonFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(this.getClass());
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }
    
    public void setPersonFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Update the stage title.
            stageManager.setStageTitle(stageManager.getPrimaryStage(), "AddressApp - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Update the stage title.
            stageManager.setStageTitle(stageManager.getPrimaryStage(), "AdressApp");
        }
    }
    
    public void loadPersonDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(PersonListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            PersonListWrapper wrapper = (PersonListWrapper) um.unmarshal(file);

            personDataManager.clearPersonData();
            personDataManager.addAllPersonData(wrapper.getPersons());

            // Save the file path to the registry.
            setPersonFilePath(file);

        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Não foi possível carregar dados do arquivo "+file.getPath());
            alert.setContentText(e.getMessage());
        }
    }
    
    public void savePersonDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(PersonListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Envolvendo nossos dados da pessoa.
            PersonListWrapper wrapper = new PersonListWrapper();
            wrapper.setPersons(personDataManager.getPersonData());

            // Enpacotando e salvando XML  no arquivo.
            m.marshal(wrapper, file);

            // Saalva o caminho do arquivo no registro.
            setPersonFilePath(file);
        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Não foi possível salvar os dados do arquivo "+file.getPath());
            alert.setContentText(e.getMessage());

            alert.showAndWait();
        }
    }
    
}
