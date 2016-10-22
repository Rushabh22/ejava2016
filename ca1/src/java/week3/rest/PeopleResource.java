
package week3.rest;

import java.util.Optional;
import java.util.UUID;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import week3.business.PeopleBean;
import week3.model.People;


@RequestScoped
@Path("/people")
public class PeopleResource {
    
    @EJB private PeopleBean peopleBean;
   
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPerson( @FormParam("name") String name, @FormParam("email") String email ) {
            System.out.println(String.format(" Person >> name: %s, email: %s", name, email));
            
              // check if person already exisits with same email
            Optional<People> opt = peopleBean.findPersonByEmail(email);
            People existingPerson = opt.get();
            if(existingPerson != null){
                System.out.println("Person already exists..");

                return (Response.status(Response.Status.OK)
				.entity("Person already exists with email:" + email)
				.build());
            }

            People  people = new People();
            people.setName(name);
            people.setEmail(email);
            people.setPid(UUID.randomUUID().toString().substring(0, 8));
      
            peopleBean.addUser(people);

            return (Response.status(Response.Status.CREATED)
                            .build());
    }
    
        @GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findCustomerById(@QueryParam("email") String email) {
		Optional<People> opt = peopleBean.findPersonByEmail(email);
                                
		if (opt.isPresent())
			return (Response.ok(opt.get().toJSON())
					.build());
                
                return (Response.status(Response.Status.NOT_FOUND)
                 .entity("Person not found with email:" + email)
                 .build());

	}
            
}
