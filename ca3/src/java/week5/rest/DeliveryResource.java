/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package week5.rest;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import static javax.ws.rs.HttpMethod.POST;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataParam;
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
    @Path("callback")
    @Produces(MediaType.APPLICATION_JSON)
    public Response callback(@QueryParam("podId") Integer pod_id, @QueryParam("ackId") String ack_id) {
        Pod pod = logisticsService.findPod(pod_id);
        System.out.println(pod.getPod_id());
        pod.setAck_id(ack_id);
        logisticsService.updatePod(pod);
        return (Response.status(Response.Status.OK).build());
    }
    
    @POST
    @Path("upload")
    @Produces(MediaType.MULTIPART_FORM_DATA)
    public Response upload( @FormDataParam("podId") Integer podId, @FormDataParam("note") String note,
            @FormDataParam("time") Long time, @FormDataParam("image") InputStream image) throws IOException {
      
        byte[] imageByte = toBytes(image);
        Pod pod = logisticsService.findPod(podId);
        pod.setNote(note);
        pod.setPod_id(podId);
        pod.setDelivery_date(new Date(time));
        pod.setImage(imageByte);
        logisticsService.updatePod(pod);
        return (Response.status(Response.Status.OK).build());
    }
    
     private byte[] toBytes(InputStream is) throws IOException {
        byte[] buffer = new byte[1024 * 8];
        int size = 0;
            BufferedInputStream bis = new BufferedInputStream(is);
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                while (-1 != (size = bis.read(buffer))) {
                    baos.write(buffer, 0, size);
                }
                buffer = baos.toByteArray();
            }
     
        return buffer;
    }

}
