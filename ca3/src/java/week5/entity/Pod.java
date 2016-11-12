package week5.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author user
 */
@Entity
public class Pod implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer pod_id;

    private String note;

    @Lob
    @Column(length=100000)
    private byte[] image;

    @Temporal(TemporalType.TIMESTAMP)
    private Date delivery_date;

    private String ack_id;

    @OneToOne
    @JoinColumn(name="pkg_id", referencedColumnName="pkg_id")
    private Delivery pkg;

    public Delivery getPkg() {
        return pkg;
    }

    public void setPkg(Delivery pkg) {
        this.pkg = pkg;
    }

    public Integer getPod_id() {
        return pod_id;
    }

    public void setPod_id(Integer pod_id) {
        this.pod_id = pod_id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }   

    public Date getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(Date delivery_date) {
        this.delivery_date = delivery_date;
    }

    public String getAck_id() {
        return ack_id;
    }

    public void setAck_id(String ack_id) {
        this.ack_id = ack_id;
    }

}