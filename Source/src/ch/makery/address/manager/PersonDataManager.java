/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.makery.address.manager;

import ch.makery.address.model.Person;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author leocg
 */
public class PersonDataManager {
    
    private ObservableList<Person> personData = FXCollections.observableArrayList();
    private static final PersonDataManager instance = new PersonDataManager();
    
    
    private PersonDataManager(){
        personData.add(new Person("Hans", "Muster"));
        personData.add(new Person("Ruth", "Mueller"));
        personData.add(new Person("Heinz", "Kurz"));
        personData.add(new Person("Cornelia", "Meier"));
        personData.add(new Person("Werner", "Meyer"));
        personData.add(new Person("Lydia", "Kunz"));
        personData.add(new Person("Anna", "Best"));
        personData.add(new Person("Stefan", "Meier"));
        personData.add(new Person("Martin", "Mueller"));
    }
    
    public static PersonDataManager getInstance(){
        return instance;
    }
    
    public ObservableList<Person> getPersonData() {
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
