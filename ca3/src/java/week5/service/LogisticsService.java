
package week5.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import week5.entity.Delivery;


@Stateless
public class LogisticsService {
    
    @PersistenceContext(unitName="ca3PU")
    private EntityManager em;
    
    public void saveDelivery(Delivery note) {
        em.persist(note);        
    }
}
