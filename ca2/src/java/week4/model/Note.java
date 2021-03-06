/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package week4.model;

import java.io.Serializable;
import java.util.Date;
import javax.json.Json;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@NamedQueries({
@NamedQuery(name = "Note.findNotesByCategory",
        query = "select n.title, n.created_date, n.userid, n.category, n.content from Note n where n.category = :category order by n.created_date desc"),
@NamedQuery(name = "Note.findAllNotes",
        query = "select n.title, n.created_date, n.userid, n.category, n.content from Note n order by n.created_date desc")
})
@Entity
@Table(name = "notes")
public class Note implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "note_id")
    private Integer noteId;
    
     @Column(name = "userid")
    private String userid;

    @Column(name = "title")
    private String title;
    
    @Column(name = "category")
    private String category;
    
    @Column(name = "content")
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date created_date;

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }


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

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public String toJson() {
        String msg = Json.createObjectBuilder()
                .add("title", getTitle())
                .add("time", getCreated_date().toString())
                .add("who", getUserid())
                .add("category", getCategory())
                .add("content", getContent())
                .build()
                .toString();

        return msg;
    }
    
    
}
