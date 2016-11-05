/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package week4.bean;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author ankur.jain
 */
@ManagedBean(name="noteBean")
@SessionScoped
public class NoteBean implements Serializable {
  private String title;
  private String category;
  private List<String> categories;
  private String content;  
  
  
}
