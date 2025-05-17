package cr.ac.ucr.orientaucr.orientaucr.service;

import cr.ac.ucr.orientaucr.orientaucr.dao_implements.CharacteristicDAOImplements;
import cr.ac.ucr.orientaucr.orientaucr.domain.Characteristic;
import java.util.LinkedList;

public class CharacteristicService {

   private CharacteristicDAOImplements data = new CharacteristicDAOImplements();

    public CharacteristicService() {}
    
    public LinkedList<Characteristic> searchCharacteristic(String search){
        return data.getAll(search);
    }
    
    public LinkedList<Characteristic> getAllCharacteristic(){
        return data.getAll();
    }
    
    public void addCharacteristic(Characteristic t) {
        data.add(t);
    }
    
    public void updateCharacteristic(Characteristic t) {
        data.update(t);
    }
    
    public void deleteCharacteristicById(String id) {
        data.deleteById(id);
    }
    
    public Characteristic findCharacteristicById(String id) {
        return data.findById(id);
    }
    
}
