/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package week3.business;

import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import week3.model.People;

@Stateless
public class PeopleBean {
    
    @PersistenceContext(unitName = "week03_ca1") 
     private EntityManager em;
    
    /**
     * addUser - method to add new people to people table
     * @param people  - People Object to persist
     */
    public void addUser(People  people){
        em.persist(people);   
    }
    
    /**
     * Finds person by Email address
     * @param email
     * @return 
     */
    public Optional<People> findPersonByEmail(String email){
        TypedQuery<People> query = em.createNamedQuery(
                            "People.findByEmailId", People.class);
        query.setParameter("email", email);
        return Optional.of(query.getSingleResult());
        
    }
    
}
