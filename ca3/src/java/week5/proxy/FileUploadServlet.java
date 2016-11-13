/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package week5.proxy;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import week5.entity.Pod;
import week5.service.LogisticsService;
import week5.util.AppConstants;

@WebServlet("/upload")
@MultipartConfig
public class FileUploadServlet extends HttpServlet {

    @EJB
    LogisticsService logisticsService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        Integer podId = Integer.valueOf(request.getParameter("podId"));
        String note = request.getParameter("note");
        Long timeLong = Long.valueOf(request.getParameter("time"));
        Date time = new Date(timeLong);
        byte[] image = readPart(request.getPart("image"));
        Pod pod = logisticsService.findPod(podId);
        pod.setNote(note);
        pod.setPod_id(podId);
        pod.setDelivery_date(time);
        pod.setImage(image);
        logisticsService.updatePod(pod);

        //notify HQ
        Client client = ClientBuilder.newBuilder()
                .register(MultiPartFeature.class)
                .build();

        MultiPart part = new MultiPart();

        // byte[] image = readImage(podId);
        MultiPart formData = new FormDataMultiPart()
                .field("teamId", AppConstants.TEAM_ID, MediaType.TEXT_PLAIN_TYPE)
                .field("podId", podId, MediaType.TEXT_PLAIN_TYPE)
                .field("callback", "http://10.10.3.186/ca3/api/callback", MediaType.TEXT_PLAIN_TYPE)
                .field("note", note, MediaType.TEXT_PLAIN_TYPE)
                .field("image", image, MediaType.APPLICATION_OCTET_STREAM_TYPE);
        formData.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);

        Response callResp = null;
        boolean status = false;

            WebTarget target = client.target("http://10.10.0.48:8080/epod/upload");
            Invocation.Builder inv = target.request();
            System.out.println("I am here, going to upload  now");
            System.out.println(">>> part: " + formData);
            boolean callStatus = false;    
            do {
                callResp = inv.post(Entity.entity(formData, formData.getMediaType()));
                System.out.println("After involing the Post >>> resp code >> " + callResp.getStatus());
            
                try {
                    Thread.currentThread().sleep(2000);
                    Pod udatedPod = logisticsService.findPod(podId);
                    if(udatedPod.getAck_id() !=  null ){
                        callStatus = true;
                    }

                } catch (InterruptedException ex) {
                    Logger.getLogger(FileUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            } while (callStatus);

    }

    private byte[] readPart(Part part) throws IOException {
        byte[] buffer = new byte[1024 * 8];
        int size = 0;
        try (InputStream is = part.getInputStream()) {
            BufferedInputStream bis = new BufferedInputStream(is);
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                while (-1 != (size = bis.read(buffer))) {
                    baos.write(buffer, 0, size);
                }
                buffer = baos.toByteArray();
            }
        }
        return buffer;
    }

}
