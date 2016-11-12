/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package week5.rest;

import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import week5.entity.Pod;
import week5.service.LogisticsService;
import week5.util.AppConstants;

/**
 * REST Web Service
 *
 * 
 */
@Path("/")
public class DeliveryResource {

    @EJB
    LogisticsService logisticsService;

    /**
     * Creates a new instance of DeliveryResource
     */
    public DeliveryResource() {
    }

    /**
     * Retrieves representation of an instance of week5.rest.DeliveryResource
     *
     * @return an Response
     */
    @GET
    @Path("items")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJson() {
        List<Object[]> list = logisticsService.getAllPodItems();
        System.out.println("Get delievery details .. >> ");
        JsonArrayBuilder jsnArrBuilder = Json.createArrayBuilder();

        list.stream().map((record) -> {
            Integer podId = (Integer) record[0];
            String address = (String) record[1];
            String name = (String) record[2];
            String phone = (String) record[3];

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
        return (javax.ws.rs.core.Response.ok(jsnArray)).build();
    } 
    
    @GET
    @Path("callback/{pod_id}/{ack_id}")
    public void callback(@PathParam("pod_id") Integer pod_id,@PathParam("ack_id") String ack_id){
        Pod pod = logisticsService.findPod(pod_id);
        pod.setAck_id(ack_id);
        logisticsService.savePod(pod);
    }   
  
}
