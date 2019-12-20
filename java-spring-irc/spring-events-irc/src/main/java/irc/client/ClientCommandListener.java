package irc.client;

import irc.events.ClientCommandIRCEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ClientCommandListener {

    private final SpringIRCClient springIrcClient;

    @EventListener
    public void doClientCommand(ClientCommandIRCEvent event){
        event.handle(springIrcClient);
    }
}
