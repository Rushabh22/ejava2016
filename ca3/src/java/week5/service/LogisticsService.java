
package week5.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import week5.entity.Delivery;
import week5.entity.Pod;


@Stateless
public class LogisticsService {
    
    @PersistenceContext(unitName="ca3PU")
    private EntityManager em;
    
    public void saveDelivery(Delivery delivery) {
        em.persist(delivery);        
    }
    
     public void savePod(Pod pod) {
        em.persist(pod);        
    }
    
  public List<Object[]> getAllPodItems() {
        TypedQuery<Object[]> query = em.createNamedQuery("Pod.findItems", Object[].class);
        return query.getResultList();
    }
 
  
  public List<Pod> findAllPods() {
       TypedQuery<Pod> query = em.createNamedQuery("Pod.findPods",Pod.class);
       return query.getResultList();
  }
}
