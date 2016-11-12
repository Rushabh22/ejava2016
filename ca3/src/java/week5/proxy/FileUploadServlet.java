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
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import week5.entity.Pod;
import week5.service.LogisticsService;

@WebServlet("/upload")
@MultipartConfig
public class FileUploadServlet extends HttpServlet{

    @EJB
    LogisticsService logisticsService;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        Integer podId = Integer.valueOf(request.getParameter("podId"));
        String note = request.getParameter("note");
        Date time = new Date(request.getParameter("time"));
        byte[] image = readPart(request.getPart("image"));
           Pod pod = new Pod();
            pod.setNote(note);
            pod.setPod_id(podId);
            pod.setDelivery_date(time);
            pod.setImage(image);
            logisticsService.updatePod(pod);
    }

    private byte[] readPart(Part part) throws IOException {
        byte[] buffer = new byte[1024*8];
        int size = 0;
        try(InputStream is = part.getInputStream()){
            BufferedInputStream bis = new BufferedInputStream(is);
            try(ByteArrayOutputStream baos = new ByteArrayOutputStream()){
                while (-1 != (size = bis.read(buffer)))
                    baos.write(buffer,0,size);
                buffer = baos.toByteArray();
            }
        }
        return buffer;
    }
    
}
