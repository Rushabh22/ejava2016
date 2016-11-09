package week4.model;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.websocket.Session;

@ApplicationScoped
@Named
public class UserSessionMap {

    private Map<String, List<Session>> sessionMap;

    @PostConstruct
    public void init() {
        System.out.println("week4.model.UserSessionMap.init()>>>>>>>>>>>>>");
        sessionMap = new ConcurrentHashMap<>();

    }

    //provide a getter for the map
    public Map<String, List<Session>> getSessionMap() {
        return this.sessionMap;
    }
}
