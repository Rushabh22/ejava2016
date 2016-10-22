package week3.model;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;


@NamedQuery(name = "Appointment.findAppointmentsByEmailId", 
		query = "select ap.appointmentId, ap.appt_date, ap.description, p.pid from People p join Appointment ap where p.email = :email")
@Entity
public class Appointment implements Serializable{
    
    @Column(name = "appt_id") 
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer appointmentId;
    
    private String description;
    
    private Date appt_date;
    
    @ManyToOne
    @JoinColumn(name = "pid", referencedColumnName = "pid")
    private People people;

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getAppt_date() {
        return appt_date;
    }

    public void setAppt_date(Date appt_date) {
        this.appt_date = appt_date;
    }

    public People getPeople() {
        return people;
    }

    public void setPeople(People people) {
        this.people = people;
    }
    
    
}
