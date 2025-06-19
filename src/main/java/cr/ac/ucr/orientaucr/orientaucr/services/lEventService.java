/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cr.ac.ucr.orientaucr.orientaucr.services;

import cr.ac.ucr.orientaucr.orientaucr.domain.Event;
import java.util.List;

public interface lEventService extends CRUD<Event>{
    void InsertUserInterestedEvent(String eventId,String userId);
    void removeUserInterestedEvent(String eventId,String userId);
   List<String> GetInterestedEventsByUser(String userId);
}
