package week5.proxy;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.BodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import week5.entity.Pod;
import week5.service.LogisticsService;

@WebServlet("/upload-proxy")
public class UploadProxy extends HttpServlet {

        @Inject
        LogisticsService logicService;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		Client client = ClientBuilder.newBuilder()
				.register(MultiPartFeature.class)
				.build();

                String teamId="f178b3ba";//req.getParameter("teamId");
                String podId="1";//req.getParameter("podId");
                String note="message1";//req.getParameter("note");
		MultiPart part = new MultiPart();

		
               byte[] image = readImage(podId);
               
               MultiPart formData = new FormDataMultiPart()
                                .field("teamId",teamId,MediaType.TEXT_PLAIN_TYPE)
				.field("podId", podId, MediaType.TEXT_PLAIN_TYPE)
                                .field("callback","http://10.10.3.186/ca3/api/callback",MediaType.TEXT_PLAIN_TYPE)
				.field("note", note, MediaType.TEXT_PLAIN_TYPE)
                        .field("image",image,MediaType.APPLICATION_OCTET_STREAM_TYPE);
		formData.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);


		//part.bodyPart(new FileDataBodyPart("image", 
				//new File("/home/cmlee/Pictures/ca3.png")));
				
		WebTarget target = client.target("http://10.10.0.48:8080/epod/upload");
		Invocation.Builder inv = target.request();
                System.out.println("I am here, going to upload  now");
		System.out.println(">>> part: " + formData);

		Response callResp = inv.post(Entity.entity(formData, formData.getMediaType()));
                System.out.println("After involing the Post");
	}
        
        protected byte[] readImage(String pod) {
            Integer podId = Integer.valueOf(pod);
            Pod pod_image = logicService.findPod(podId);
            byte[] image = pod_image.getImage();
            return image;
        }

	
	
}
