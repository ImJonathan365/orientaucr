/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.orientaucr.orientaucr.jpa;

import cr.ac.ucr.orientaucr.orientaucr.domain.Event;
import cr.ac.ucr.orientaucr.orientaucr.repository.lEventRepository;
import cr.ac.ucr.orientaucr.orientaucr.services.lEventService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class EventServiceJPA implements lEventService{
   @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private lEventRepository repo;
    
    @Override
    public List<Event> getAll(String search) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Event> getAll() {
        return repo.findAll();
    }

   @Override
public void add(Event t) {
    if (t.getEventId() == null || t.getEventId().isEmpty()) {
        t.setEventId(UUID.randomUUID().toString());
    }
    if (t.getCampusId() == null || t.getCampusId().trim().isEmpty()) {
        t.setCampusId(null);
    }
    if (t.getSubcampusId() == null || t.getSubcampusId().trim().isEmpty()) {
        t.setSubcampusId(null);
    }

    repo.save(t);
}


    @Override
    public void update(Event t) {
    if (t.getCampusId() == null || t.getCampusId().trim().isEmpty()) {
        t.setCampusId(null);
    }
    if (t.getSubcampusId() == null || t.getSubcampusId().trim().isEmpty()) {
        t.setSubcampusId(null);
    } 
     if (repo.existsById(t.getEventId())) {
            repo.save(t);
              } else {
        throw new EntityNotFoundException("Event not found");
    } 
    }
    @Override
    public void deleteById(String i) {
        repo.deleteById(i);
    }

    @Override
    public Event findById(String id) {
    return repo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Evento no encontrado"));
}
  @Override
public void InsertUserInterestedEvent(String eventId, String userId) {
    StoredProcedureQuery query = entityManager
            .createStoredProcedureQuery("sp_InsertUserInterestedEvent")
            .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
            .registerStoredProcedureParameter(2, String.class, ParameterMode.IN);

    query.setParameter(1, eventId);
    query.setParameter(2, userId);
    query.execute();
}
}
