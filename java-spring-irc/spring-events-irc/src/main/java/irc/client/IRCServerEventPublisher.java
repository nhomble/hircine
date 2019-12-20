package irc.client;

import irc.events.RemoteIRCEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class IRCServerEventPublisher {

    private final ApplicationEventPublisher publisher;
    private final SpringIRCClient springIrcClient;

    public void check() {
        java.util.Optional<String> s;
        while ((s = springIrcClient.getIRC().check()).isPresent()) {
            publisher.publishEvent(new RemoteIRCEvent(s.get()));
            check();
        }
    }
}
