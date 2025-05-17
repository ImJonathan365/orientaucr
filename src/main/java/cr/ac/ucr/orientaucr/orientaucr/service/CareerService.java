
package cr.ac.ucr.orientaucr.orientaucr.service;

import cr.ac.ucr.orientaucr.orientaucr.dao_implements.CareerDAOImplements;
import cr.ac.ucr.orientaucr.orientaucr.domain.Career;
import java.util.LinkedList;


public class CareerService {
    private static CareerDAOImplements data = new CareerDAOImplements();
    
    public static LinkedList<Career> getAllCareers(){
        //System.out.println("Lista: "+data.getAll().get(0).getCareer_name());
        return data.getAll();
    }
    
    public static void addCareer(Career career){
        data.add(career);
    }
    
}
