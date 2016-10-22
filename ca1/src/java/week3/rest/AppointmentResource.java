
package week3.rest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import week3.business.PeopleBean;
import week3.model.People;


@RequestScoped
@Path("/appointment")
public class AppointmentResource {
    
    @EJB private PeopleBean peopleBean;
    
        @GET
        @Path("{email}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAppointments(@PathParam("email") String email) {
            
		List<Object> list = peopleBean.findAppointments(email);
                
                System.out.println("list >>" + list.size());
                
//                if(list != null && list.size() > 0){
//                    JsonArray json = Json.createArrayBuilder();
                    
                return (Response.status(Response.Status.OK)
                 .build());    
                }
                
                                
            
}
