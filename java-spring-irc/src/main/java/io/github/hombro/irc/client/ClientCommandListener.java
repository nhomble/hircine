package io.github.hombro.irc.client;

import io.github.hombro.irc.events.ClientCommandIRCEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ClientCommandListener {

    private final IRCClient ircClient;

    @EventListener
    public void doClientCommand(ClientCommandIRCEvent event){
        event.handle(ircClient);
    }
}
