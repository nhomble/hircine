package irc.client;

import irc.events.LocalIRCEvent;
import irc.events.RemoteIRCEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PrintingRemoteIRCEventListener {

    @EventListener
    public void print(RemoteIRCEvent event) {
        System.out.println("[Server] " + event.getRemoteMessage());
    }

    @EventListener
    public void printLocal(LocalIRCEvent localIRCEvent){
        System.out.println("[Local] " + localIRCEvent.getLine());
    }
}
