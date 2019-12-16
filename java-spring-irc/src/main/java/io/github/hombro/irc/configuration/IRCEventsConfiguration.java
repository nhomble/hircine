package io.github.hombro.irc.configuration;

import io.github.hombro.irc.client.IRCServerEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@Import(ScheduleConfiguration.class)
public class IRCEventsConfiguration {

    private IRCServerEventPublisher publisher;

    @Async("asyncExecutor")
    @Scheduled(initialDelay = 500, fixedDelay = 500)
    void checkIRC() {
        publisher.check();
    }

    @Autowired
    public void setPublisher(IRCServerEventPublisher publisher) {
        this.publisher = publisher;
    }
}
