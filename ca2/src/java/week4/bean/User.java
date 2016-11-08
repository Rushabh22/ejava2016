package week4.bean;

import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Named
@SessionScoped
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

        private String name;
        public User() {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            HttpServletRequest request = (HttpServletRequest)context.getRequest();
            name=request.getRemoteUser();
        }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	

	public String logout() {
                FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
                return "/index";
	}
}
