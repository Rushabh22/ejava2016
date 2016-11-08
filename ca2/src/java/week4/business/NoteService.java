
package week4.business;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import week4.model.Note;


@Stateless
public class NoteService {
    
    @PersistenceContext(unitName="week04PU")
    private EntityManager em;
    
    private static final String ALL = "ALL";
          
    public void saveNote(Note note) {
        em.persist(note);        
    }
    
    public List<Object[]> findNotesByCategory(String category) {
        String queryString =  "Note.findNotesByCategory";
        if(ALL.equalsIgnoreCase(category)){
            queryString = "Note.findAllNotes";
        }
        TypedQuery<Object[]> query = em.createNamedQuery( queryString, Object[].class);
        if(!ALL.equalsIgnoreCase(category)){
            query.setParameter("category", category);
        }
        
        return query.getResultList();
    }
}
