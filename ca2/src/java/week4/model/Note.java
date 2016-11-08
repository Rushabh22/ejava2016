/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package week4.model;

import java.io.Serializable;
import java.util.Date;
import javax.json.Json;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@NamedQuery(name = "Note.findNotesByCategory",
        query = "select n.TITLE, n.CREATED_DATE, n.USERNAME, n.CATEGORY, n.CONTENT from Note n where n.CATEGORY = :category")
@Entity
@Table(name = "NOTES")
public class Note implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer NOTE_ID;

    private String USERNAME;

    private String TITLE;

    private String CATEGORY;

    private String CONTENT;

    @Temporal(TemporalType.TIMESTAMP)
    private Date CREATED_DATE;

    public Integer getNOTE_ID() {
        return NOTE_ID;
    }

    public void setNOTE_ID(Integer NOTE_ID) {
        this.NOTE_ID = NOTE_ID;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getCATEGORY() {
        return CATEGORY;
    }

    public void setCATEGORY(String CATEGORY) {
        this.CATEGORY = CATEGORY;
    }

    public String getCONTENT() {
        return CONTENT;
    }

    public void setCONTENT(String CONTENT) {
        this.CONTENT = CONTENT;
    }

    public Date getCREATED_DATE() {
        return CREATED_DATE;
    }

    public void setCREATED_DATE(Date CREATED_DATE) {
        this.CREATED_DATE = CREATED_DATE;
    }

    public String toJson() {
        String msg = Json.createObjectBuilder()
                .add("title", getTITLE())
                .add("time", getCREATED_DATE().toString())
                .add("who", getUSERNAME())
                .add("category", getCATEGORY())
                .add("content", getCONTENT())
                .build()
                .toString();

        return msg;
    }
    
    
}
