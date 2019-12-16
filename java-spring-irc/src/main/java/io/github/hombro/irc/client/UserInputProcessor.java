package io.github.hombro.irc.client;

import io.github.hombro.irc.events.ClientCommandIRCEvent;
import io.github.hombro.irc.events.LocalIRCEvent;
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
            String[] parts = event.getLine().toLowerCase().split(" ");
            switch (parts[0]) {
                case "/quit":
                    publisher.publishEvent(new QuitIRCEvent());
                    break;
                case "/list-users":
                    publisher.publishEvent(new ClientCommandIRCEvent(IRCClient::listUsers));
                    break;
                case "/list-channels":
                    publisher.publishEvent(new ClientCommandIRCEvent(IRCClient::listChannels));
                    break;
                case "/change-channel":
                    publisher.publishEvent(new ClientCommandIRCEvent(c -> c.changeChannel(parts[1])));
                    break;
                case "/where-am-i":
                    publisher.publishEvent(new ClientCommandIRCEvent(c -> {
                        publisher.publishEvent(new LocalIRCEvent(String.format(
                                "channel='%s', nickname='%s'",
                                c.getChannel(), c.getNickName()
                        )));
                    }));
            }
        } else {
            publisher.publishEvent(new ClientCommandIRCEvent(c -> c.sendMessage(event.getLine())));
        }
    }
}
