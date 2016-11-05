
package week4.business;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import week4.model.Note;

@Stateless
public class NoteBean {
    
    @PersistenceContext(unitName = "week04PU")
    private EntityManager em;
    
    
    public void saveNote(Note note) {
        em.persist(note);
    }
    
     public List<Object[]> findNotesByCategory(String category) {
        TypedQuery<Object[]> query = em.createNamedQuery("Note.findNotesByCategory", Object[].class);
        query.setParameter("category", category);
        return query.getResultList();
    }
}
