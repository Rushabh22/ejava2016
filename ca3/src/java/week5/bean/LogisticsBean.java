/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package week5.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import week5.entity.Delivery;
import week5.entity.Pod;
import week5.service.LogisticsService;


@Named
@SessionScoped
public class LogisticsBean implements Serializable{
    
    @Inject
    LogisticsService logisticsService;
    
    private String name;
    
    private String address;
    
    private String phone;
    
    private String note;
    
    private Date created_date;    
    
    private Date delivery_date;
    
    private String ack_id;
    
    private List<LogisticsBean> logisticsBeans;

    public LogisticsService getLogisticsService() {
        return logisticsService;
    }

    public void setLogisticsService(LogisticsService logisticsService) {
        this.logisticsService = logisticsService;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
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
    
    public String submit(){
        Delivery delivery = new Delivery();
        delivery.setName(name);
        delivery.setAddress(address);
        delivery.setPhone(phone);
        delivery.setCreate_date(new Date());        
        delivery.setPod(new Pod());
        logisticsService.saveDelivery(delivery);
        return null;
    }
    

    public List<LogisticsBean> getLogisticsDetails(){
        logisticsService.getAllPodItems();
        return null;
    }    

}
