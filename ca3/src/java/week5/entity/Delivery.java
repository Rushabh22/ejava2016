package week5.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
@Table(name="delivery")
public class Delivery implements Serializable {
    private static final long serialVersionUID = 1l;
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer pkg_id;
    
    private String name;
    
    private String address;
    
    private String phone;
    
    @OneToOne(mappedBy="pkg")    
    private Pod pod;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date create_date;    

    public Integer getPkg_id() {
        return pkg_id;
    }

    public void setPkg_id(Integer pkg_id) {
        this.pkg_id = pkg_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public Pod getPod() {
        return pod;
    }

    public void setPod(Pod pod) {
        this.pod = pod;
    }

    @Override
    public String toString() {
        return "Delivery{" + "pkg_id=" + pkg_id + ", name=" + name + ", address=" + address + ", phone=" + phone + ", pod=" + pod + ", create_date=" + create_date + '}';
    }
    
    
}
