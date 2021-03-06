package week4.web;

import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SessionScoped
@Named
public class UserSession implements Serializable {

	private static final long serialVersionUID = 1L;

        
	@Inject private Principal user;

	public String getUsername() {
		return (user.getName());
	}
	public void setUserName(String n) { }

	public String logout() {
                String result = "/index?faces-redirect=true";
		ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest req = (HttpServletRequest)ctx.getRequest();
		HttpServletResponse resp = (HttpServletResponse)ctx.getResponse();
		try {
		req.logout();
		} catch (ServletException ex) { 
                result =  "/error?faces-redirect=true";   
                }
        
		//FacesContext.getCurrentInstance().
                return result;
	}
        
        public String createNote() {
           return null; 
        }
        public String listNote() {
            return null;
        }

	
}
