package week4.business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.websocket.EndpointConfig;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@RequestScoped
@ServerEndpoint("/note/{category}")
public class NoteEndpoint {

	private static Map<String, Object> sessMap;

	@OnOpen
    public void open(Session session, @PathParam("category") String category) {
        System.out.println(">>> Open session id : " + session.getId());
        System.out.println(">>> open > category : " + category);
        if (sessMap == null) {
            sessMap = session.getUserProperties();
        }
        List<Session> sessions = (List<Session>) sessMap.get(category);
        if (sessions == null) {
            sessions = Collections.synchronizedList(new ArrayList());
        }
        sessions.add(session);
        sessMap.put(category, sessions);
    }

	@OnMessage
	public void message(String text) {
		String msg = Json.createObjectBuilder()
				.add("text", text)
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
}
