/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package week3.business;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import week3.model.People;

@Stateless
public class PeopleBean {
    
    @PersistenceContext(unitName = "week03_ca1") 
     private EntityManager em;
    
    public void addUser(People  people){
        em.persist(people);   
    }
    
}
