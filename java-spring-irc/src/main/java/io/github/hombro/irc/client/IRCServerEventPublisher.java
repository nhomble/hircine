package io.github.hombro.irc.client;

import io.github.hombro.irc.events.RemoteIRCEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class IRCServerEventPublisher {

    private final ApplicationEventPublisher publisher;
    private final IRCClient ircClient;

    public void check() {
        java.util.Optional<String> s;
        while ((s = ircClient.check()).isPresent()) {
            publisher.publishEvent(new RemoteIRCEvent(s.get()));
            check();
        }
    }
}
