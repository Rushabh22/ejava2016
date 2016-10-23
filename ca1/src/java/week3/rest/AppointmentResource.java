package week3.rest;

import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.json.JsonArray;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import week3.business.PeopleBean;

@RequestScoped
@Path("/appointment")
public class AppointmentResource {

    @EJB
    private PeopleBean peopleBean;

    @GET
    @Path("{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAppointments(@PathParam("email") String email) {

        List<Object[]> list = peopleBean.findAppointments(email);
        System.out.println("email >> " + email);
        JsonArrayBuilder jsnArrBuilder = Json.createArrayBuilder();

        list.stream().map((record) -> {
            Integer appointmentId = (Integer) record[0];
            Long dateTime = ((java.util.Date) record[1]).getTime();
            String description = (String) record[2];
            String personId = (String) record[3];
            System.out.println("***************************************");
            System.out.println("appointmentId >> " + appointmentId);
            System.out.println("dateTime >> " + dateTime);
            System.out.println("description >> " + description);
            System.out.println("personId >> " + personId);
            JsonObject jsonObj = Json.createObjectBuilder()
                    .add("appointmentId", appointmentId)
                    .add("dateTime", dateTime)
                    .add("description", description)
                    .add("personId", personId)
                    .build();
            return jsonObj;
        }).forEachOrdered((jsonObj) -> {
            jsnArrBuilder.add(jsonObj);
        });
        System.out.println("***************************************");
        System.out.println("list size >> " + list.size());
        JsonArray jsnArray = jsnArrBuilder.build();
        System.out.println(jsnArray);
        return (Response.ok(jsnArray)).build();
    }
}
