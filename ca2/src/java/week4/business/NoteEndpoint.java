package week4.business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@Stateless
@ServerEndpoint("/note/{category}")
public class NoteEndpoint {

    private static Map<String, Object> sessionMap;
    private Session session;
    
    @OnOpen
    public void open(Session session, @PathParam("category") String category) {
        this.session = session;
        System.out.println(">>> Open session id : " + session.getId());
        System.out.println(">>> open > category : " + category);
        if (sessionMap == null) {
            sessionMap = session.getUserProperties();
        }
        List<Session> sessions = (List<Session>) sessionMap.get(category);
        if (sessions == null) {
            sessions = Collections.synchronizedList(new ArrayList());
        }
        sessions.add(session);
        sessionMap.put(category, sessions);
    }

    @OnMessage
    public void message(String text, @PathParam("category") String category) {
        System.out.println(">>> message : " + text);
        System.out.println(">>> message > category : " + category);
        String msg = Json.createObjectBuilder()
                .add("text", text)
                .add("title", "title")
                .add("time", (new Date()).toString())
                .add("who", "Who")
                .add("category", category)
                .add("content", "content")
                .build()
                .toString();
        List<Session> sessList;
        if("All".equals(category)){
            sessList = (List<Session>) sessionMap.get(category);
            sessList.forEach((s) -> {
                sendMessage(s, msg);
            });
        }else{           
            session.getOpenSessions().forEach((s) -> {
                sendMessage(s, msg);
            });     
        }
    }

    @OnClose
    public void close(Session sess, @PathParam("category") String category) {

        List<Session> sessions = (List<Session>) sessionMap.get(category);
        for (Session s : sessions) {
            if (sess.equals(s)) {
                System.out.println(">>> Removed Session : " + s.getId());
                sessions.remove(sess);
                break;
            }
        }
        sessionMap.put(category, sessions);
    }

    @OnError
    public void onError(Throwable t) {
        System.out.println(">>> Websocket error : " + t.getMessage());
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
}
