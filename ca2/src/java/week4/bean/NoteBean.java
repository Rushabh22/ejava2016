/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package week4.bean;

import java.io.Serializable;
import java.security.Principal;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import week4.business.NoteService;
import week4.model.Note;
import week4.business.NoteEndpoint;

@ManagedBean(name="noteBean")
@SessionScoped
public class NoteBean implements Serializable {
  private static final long serialVersionUID = 1l;
  
  @Inject private Principal user;
  
  private String title;
  private String category = "Social";
  private String content; 

  @Inject 
  NoteService noteService;
  
  @Inject
  private NoteEndpoint noteEndPoint;
  
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    public void create() {
        Note note = new Note();
        note.setCategory(category);
        note.setContent(content);
        note.setTitle(title);
        note.setCreated_date(new Date());
        note.setUserid(user.getName());
        noteService.saveNote(note);
        noteEndPoint.display(note);
    }
      
}
