package week5.rest;

import java.util.ArrayList;
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
import week5.util.AppConstants;


@RequestScoped
public class DeliveryItemResource {


    @GET
    @Path("/items")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAppointments() {

        List<Object[]> list =  new ArrayList<>(); //peopleBean.findAppointments(email);
        System.out.println("Get delievery details .. >> ");
        JsonArrayBuilder jsnArrBuilder = Json.createArrayBuilder();
             
        list.stream().map((record) -> {
       //     Integer podId = (Integer) record[0];
        //    String address = (String) record[1];
         //   String name = (String) record[2];
          //  String phone = (String) record[3];
            
            Integer podId = 111;
            String address = "The Luxurie";
            String name = "amar";
            String phone = "94659939";
            System.out.println("***************************************");
            System.out.println("podId >> " + podId);
            System.out.println("address >> " + address);
            System.out.println("name >> " + name);
            System.out.println("phone >> " + phone);
            JsonObject jsonObj = Json.createObjectBuilder()
                    .add("teamId", AppConstants.TEAM_ID)
                    .add("podId", podId)
                    .add("address", address)
                    .add("name", name)
                    .add("phone", phone)
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
