/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.makery.address.model.manager;

import ch.makery.address.MainApp;
import ch.makery.address.model.Person;
import ch.makery.address.model.ListWrapper;
import java.io.File;
import java.util.prefs.Preferences;
import javafx.scene.control.Alert;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlSeeAlso;

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
            stageManager.setPrimaryStageTitle("AddressApp - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Update the stage title.
            stageManager.setPrimaryStageTitle("AdressApp");
        }
    }
    
    public void loadPersonDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(ListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            ListWrapper <Person> wrapper = (ListWrapper <Person>) um.unmarshal(file);

            personDataManager.clearPersonData();
            personDataManager.addAllPersonData(wrapper.getList());

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
                    .newInstance(ListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Envolvendo nossos dados da pessoa.
            ListWrapper <Person> wrapper = new ListWrapper();
            wrapper.setList(personDataManager.getPersonData());

            // Enpacotando e salvando XML  no arquivo.
            m.marshal(wrapper, file);

            // Saalva o caminho do arquivo no registro.
            setPersonFilePath(file);
        } catch (JAXBException e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Não foi possível salvar os dados do arquivo "+file.getPath());
            System.out.println(e.getErrorCode()+"\n"+e.getLocalizedMessage()+"\n"+e.getMessage()+"\n"+e.toString());
            alert.setContentText(e.getMessage());

            alert.showAndWait();
        }
    }
    
}
