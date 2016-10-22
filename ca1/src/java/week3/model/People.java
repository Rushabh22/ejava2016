/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package week3.model;


import java.io.Serializable;
import java.util.Collection;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@NamedQuery(name = "People.findByEmailId", 
		query = "select p from People p where p.email = :email")
@Table(name = "people")
@Entity
public class People implements Serializable{

    @Id
    private String pid;
   
    private String name;
    
    private String email;
    
    @OneToMany(mappedBy = "people")
    private Collection<Appointment> appointments;

    public JsonObject toJSON() {
            return  (Json.createObjectBuilder()
                                .add("pid", pid)
				.add("name", name)
				.add("email", email)
				.build());  
	}
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Collection<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Collection<Appointment> appointments) {
        this.appointments = appointments;
    }   
    
}
