/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.orientaucr.orientaucr.jpa;

import cr.ac.ucr.orientaucr.orientaucr.domain.Subcampus;
import cr.ac.ucr.orientaucr.orientaucr.repository.SubcampusRepository;
import cr.ac.ucr.orientaucr.orientaucr.services.lSubcampus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author carlo
 */
@Service
public class SubcampuesServiceJPA implements lSubcampus{
 @Autowired
    private SubcampusRepository repo;
  @PersistenceContext
    private EntityManager entityManager;
  
@Override
public List<Subcampus> getAll(String campusId) {
    StoredProcedureQuery query = entityManager
        .createStoredProcedureQuery("sp_GetSubcampusByCampusId", Subcampus.class)
        .registerStoredProcedureParameter(1, String.class, ParameterMode.IN);

    query.setParameter(1, campusId);
    query.execute();

    @SuppressWarnings("unchecked")
    List<Subcampus> subcampusList = query.getResultList();

    return subcampusList;
}
    @Override
    public List<Subcampus> getAll() {
 return repo.findAll();    }

    @Override
    public void add(Subcampus t) {
   repo.save(t);
    }

    @Override
    public void update(Subcampus t) {
 repo.save(t);
    }

    @Override
    public void deleteById(String i) {
          throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody

    }

    @Override
    public Subcampus findById(String i) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
