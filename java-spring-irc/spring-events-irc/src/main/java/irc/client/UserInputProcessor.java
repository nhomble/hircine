package irc.client;

import irc.events.ClientCommandIRCEvent;
import irc.events.LocalIRCEvent;
import irc.events.QuitIRCEvent;
import irc.events.UserInputIRCEvent;
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
                    publisher.publishEvent(new ClientCommandIRCEvent(s -> s.getIRC().listUsers()));
                    break;
                case "/list-channels":
                    publisher.publishEvent(new ClientCommandIRCEvent(s -> s.getIRC().listChannels()));
                    break;
                case "/change-channel":
                    publisher.publishEvent(new ClientCommandIRCEvent(c -> c.getIRC().changeChannel(parts[1])));
                    break;
                case "/where-am-i":
                    publisher.publishEvent(new ClientCommandIRCEvent(c -> {
                        publisher.publishEvent(new LocalIRCEvent(String.format(
                                "channel='%s', nickname='%s'",
                                c.getIRC().getChannel(), c.getIRC().getNickName()
                        )));
                    }));
            }
        } else {
            publisher.publishEvent(new ClientCommandIRCEvent(c -> c.getIRC().sendMessage(event.getLine())));
        }
    }
}
