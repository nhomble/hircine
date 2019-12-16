package io.github.hombro.irc.client;

import io.github.hombro.irc.events.RemoteIRCEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PrintingRemoteIRCEventListener {

    @EventListener
    public void print(RemoteIRCEvent event) {
        System.out.println("[Server] " + event.getRemoteMessage());
    }
}
