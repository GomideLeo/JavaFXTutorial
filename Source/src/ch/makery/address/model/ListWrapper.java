package ch.makery.address.model;

import java.util.List;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Classe auxiliar para envolver uma lista de pessoas. Esta é usada para salvar a
 * lista de pessoas em XML.
 * 
 * @author Marco Jakob
 */
@XmlRootElement
public class ListWrapper <T>{
    
    private List<T> list;

    @XmlElement
    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}