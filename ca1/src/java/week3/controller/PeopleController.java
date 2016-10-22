
package week3.controller;

import java.util.UUID;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import week3.business.PeopleBean;
import week3.model.People;


@WebServlet("/api/people")
public class PeopleController extends HttpServlet{
    
    @EJB private PeopleBean peopleBean;
    
  @Override  
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException{
      String name = req.getParameter("name");
      String email = req.getParameter("email");
      System.out.println("Inside People Controller >>>" + name + "  " + email );
      
      People  people = new People();
      people.setName(name);
      people.setEmail(email);
      people.setPid(UUID.randomUUID().toString().substring(0, 8));
      
      peopleBean.addUser(people);
      

  }

        
    
    
}
