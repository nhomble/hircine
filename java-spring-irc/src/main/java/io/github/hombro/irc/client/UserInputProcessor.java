package io.github.hombro.irc.client;

import io.github.hombro.irc.events.ClientCommandIRCEvent;
import io.github.hombro.irc.events.QuitIRCEvent;
import io.github.hombro.irc.events.UserInputIRCEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserInputProcessor {
    private final ApplicationEventPublisher publisher;

    @EventListener
    public void listenToUserInput(UserInputIRCEvent event) {
        if (event.getLine().startsWith("/")) {
            switch(event.getLine().toLowerCase()){
                case "/quit":
                    publisher.publishEvent(new QuitIRCEvent());
                    break;
            }
        } else {
            publisher.publishEvent(new ClientCommandIRCEvent(c -> c.sendMessage(event.getLine())));
        }
    }
}
