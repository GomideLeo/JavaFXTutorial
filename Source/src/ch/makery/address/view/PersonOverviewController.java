package ch.makery.address.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ch.makery.address.MainApp;
import ch.makery.address.model.manager.FileManager;
import ch.makery.address.model.Person;
import ch.makery.address.model.dao.PersonDAO;
import ch.makery.address.util.DateUtil;
import ch.makery.address.model.manager.PersonDataManager;
import ch.makery.address.model.manager.StageManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class PersonOverviewController {
    
    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;
    
    private ObservableList<Person> personData = FXCollections.observableArrayList();
    private PersonDataManager personDataManager;
    private StageManager stageManager;
    private PersonDAO personDAO;

    
    public PersonOverviewController() {
        personDataManager = PersonDataManager.getInstance();
        stageManager = StageManager.getInstance();
        personDAO = PersonDAO.getInstance();
    }

    @FXML
    private void initialize() {
        personTable.setItems((ObservableList<Person>) personDataManager.getPersonData());
        firstNameColumn.setCellValueFactory(
                cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(
                cellData -> cellData.getValue().lastNameProperty());

        // Limpa os detalhes da pessoa.
        showPersonDetails(null);

        // Detecta mudanças de seleção e mostra os detalhes da pessoa quando houver mudança.
        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
    }

    
    private void showPersonDetails(Person person) {
        if (person != null) {
            // Preenche as labels com informações do objeto person.
            firstNameLabel.setText(person.getFirstName());
            lastNameLabel.setText(person.getLastName());
            streetLabel.setText(person.getStreet());
            postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
            cityLabel.setText(person.getCity());
            birthdayLabel.setText(DateUtil.format(person.getBirthday()));            // birthdayLabel.setText(...);
        } else {
            // Person é null, remove todo o texto.
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            streetLabel.setText("");
            postalCodeLabel.setText("");
            cityLabel.setText("");
            birthdayLabel.setText("");
        }
    }
    
    
    /**
     * Chamado quando o usuário clica no botão novo. Abre uma janela para editar
     * detalhes da nova pessoa.
     */
    @FXML
    private void handleNewPerson() {
        Person tempPerson = new Person();
        boolean okClicked = stageManager.showPersonEditDialog(tempPerson);
        if (okClicked) {
            personDataManager.addPersonData(tempPerson);
            
        }
    }

    /**
     * Chamado quando o usuário clica no botão edit. Abre a janela para editar
     * detalhes da pessoa selecionada.
     */
    @FXML
    private void handleEditPerson() {
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = stageManager.showPersonEditDialog(selectedPerson);
            if (okClicked) {
                showPersonDetails(selectedPerson);
            }

        } else {
            // Nada seleciondo.
            Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Nenhuma seleção");
                alert.setHeaderText("Nenhuma Pessoa Selecionada");
                alert.setContentText("Por favor, selecione uma pessoa na tabela.");
                alert.showAndWait();
        }
    }


    /**
    * Chamado quando o usuário clica no botão delete.
    */
   @FXML
   private void handleDeletePerson() {
       int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
       if (selectedIndex >= 0) {
           personTable.getItems().remove(selectedIndex);
       } else {
           // Nada selecionado.

       Alert alert = new Alert(AlertType.WARNING);
               alert.setTitle("Nenhuma seleção");
               alert.setHeaderText("Nenhuma Pessoa Selecionada");
               alert.setContentText("Por favor, selecione uma pessoa na tabela.");

               alert.showAndWait();
       }
   }
}