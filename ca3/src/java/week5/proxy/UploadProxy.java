package week5.proxy;

import java.io.File;
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

                String teamId=req.getParameter("teamId");
                String podId=req.getParameter("podId");
                String note=req.getParameter("note");
		MultiPart part = new MultiPart();

		
               byte[] image = readImage(podId);
               
		MultiPart formData = new FormDataMultiPart()
                                .field("teamId",teamId,MediaType.TEXT_PLAIN_TYPE)
				.field("podId", podId, MediaType.TEXT_PLAIN_TYPE)
                                .field("callback","http://10.10.24.140/ca3/callback",MediaType.TEXT_PLAIN_TYPE)
				.field("note", note, MediaType.TEXT_PLAIN_TYPE)
				.field("image", image, MediaType.MULTIPART_FORM_DATA_TYPE);
		formData.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);


		//part.bodyPart(new FileDataBodyPart("image", 
				//new File("/home/cmlee/Pictures/ca3.png")));
				
		WebTarget target = client.target("http://10.10.0.48:8080/epod/upload");
		Invocation.Builder inv = target.request();
                System.out.println("I am here, going to upload  now");
		System.out.println(">>> part: " + formData);

		Response callResp = inv.post(Entity.entity(formData, formData.getMediaType()));
                System.out.println("After involing the Post");
		System.out.println(">> call resp:" + callResp.getStatus());
	}
        
        protected byte[] readImage(String pod) {
            Integer podId = Integer.valueOf(pod);
            Pod pod_image = logicService.findPod(podId);
            byte[] image = pod_image.getImage();
            return image;
        }

	
	
}
