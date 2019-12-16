package io.github.hombro.irc.client;

import io.github.hombro.irc.events.IRCEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LoggingIRCEventListener {

    @Async("asyncExecutor")
    @EventListener
    public void onApplicationEvent(IRCEvent ircEvent) {
        log.trace("IRCEvent={}", ircEvent);
    }
}
