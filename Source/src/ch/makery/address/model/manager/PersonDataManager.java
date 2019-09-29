/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.makery.address.model.manager;

import ch.makery.address.model.Person;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ch.makery.address.model.dao.PersonDAO;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersonDataManager {
    
    private List<Person> personData = FXCollections.observableArrayList();
    private final PersonDAO personDAO = PersonDAO.getInstance();
    private static final PersonDataManager instance = new PersonDataManager();
    
    
    private PersonDataManager(){
        try {
            personData.addAll(personDAO.listAll());
        } catch (Exception ex) {
            Logger.getLogger(PersonDataManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static PersonDataManager getInstance(){
        return instance;
    }
    
    public List<Person> getPersonData() {
        return personData;
    }
    
    public void clearPersonData(){
        personData.clear();
    }
    
    public void addAllPersonData(List<Person> personListToAdd){
        personData.addAll(personListToAdd);
    }
    
    public void addPersonData(Person personToAdd){
        personData.add(personToAdd);
    }
}