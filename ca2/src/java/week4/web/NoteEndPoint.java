package week4.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import week4.business.NoteService;
import week4.model.UserSessionMap;

@RequestScoped
@ServerEndpoint("/note/{category}")
public class NoteEndPoint {

    private Session session;
        
    @Inject
    private UserSessionMap userSessionMap;

    @EJB
    private NoteService noteService;

	@OnOpen
	public void open(Session sess, @PathParam("category") String category) {
            session = sess;
            System.out.println(">>> session id: " + sess.getId() + "   " + category);

             session.getUserProperties().put("category", category);
            Map<String, List<Session>> sessionMap = userSessionMap.getSessionMap();
            List<Session> sessions = sessionMap.get(category);
            if (sessions == null) {
                sessions = Collections.synchronizedList(new ArrayList());
            }
            sessions.add(session);
            sessionMap.put(category, sessions);
            System.out.println(">>> active sessions for category: " + sessionMap.size() );
            String notes = getNotes(category);

            sendMessage(session, notes);
	}

	@OnMessage
	public void message(String text) {
		String msg = Json.createObjectBuilder()
				.add("category requested >>>", text)
				.add("time", (new Date()).toString())
				.build()
				.toString();

		for (Session s: session.getOpenSessions())
			try {
				s.getBasicRemote().sendText(msg);
			} catch (IOException ex) {
				try { s.close(); } catch (IOException e) {} 
			}
	}
        
    @OnClose
    public void close(Session sess ) {
        Map<String, List<Session>> sessionMap = userSessionMap.getSessionMap(); 
        sessionMap.values().forEach(sessions ->{
            Iterator<Session> sessIt = sessions.listIterator();
            while(sessIt.hasNext()){
                Session s = sessIt.next();
                if (sess.equals(s)) {
                System.out.println(">>> Removed Session : " + s.getId());
                sessIt.remove();
            }
            }
        });
                
       System.out.println(">>> active sessions for category: " + sessionMap.size() );        
    }
    
    
    
    public void notifyAllActiveUsers(String category) {
        Map<String, List<Session>> sessionMap = userSessionMap.getSessionMap();
        String notes = getNotes(category);
        List<Session> sessions = sessionMap.get(category);
        if (sessions != null) {
            sessions.forEach(sess -> {
                sess.getOpenSessions().forEach(s ->{
                if (s.isOpen() && category.equalsIgnoreCase((String)s.getUserProperties().get("category"))) {
                        sendMessage(s, notes);
                }
            });    
        });

    }
   }

    private void sendMessage(Session s, String msg) {
        System.out.println("Message > Sessions : " + s.getId());
        try {
            s.getBasicRemote().sendText(msg);
        } catch (IOException ex) {
            try {
                s.close();
            } catch (IOException e) {
            }
        }
    }

    private String getNotes(String categoryCriteria) {
        List<Object[]> list = noteService.findNotesByCategory(categoryCriteria);
        JsonArrayBuilder jsnArrBuilder = Json.createArrayBuilder();

        list.stream().map((record) -> {
            String title = (String) record[0];
            Long createdDate = ((java.util.Date) record[1]).getTime();
            String createdBy = (String) record[2];
            String category = (String) record[3];
            String content = (String) record[4];
            System.out.println("***************************************");
            System.out.println("title >> " + title);
            System.out.println("createdDate >> " + createdDate);
            System.out.println("createdBy >> " + createdBy);
            System.out.println("category >> " + category);
            System.out.println("content >> " + content);
            JsonObject jsonObj = Json.createObjectBuilder()
                    .add("title", title)
                    .add("time", createdDate)
                    .add("who", createdBy)
                    .add("category", category)
                    .add("content", content)
                    .build();
            return jsonObj;
        }).forEachOrdered((jsonObj) -> {
            jsnArrBuilder.add(jsonObj);
        });
        System.out.println("***************************************");
        System.out.println("list size >> " + list.size());
        JsonArray jsnArray = jsnArrBuilder.build();
        System.out.println(jsnArray.toString());
        return jsnArray.toString();
    }
}
