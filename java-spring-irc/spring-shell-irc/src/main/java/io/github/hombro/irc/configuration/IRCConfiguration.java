package io.github.hombro.irc.configuration;

import io.github.hombro.irc.client.IRCSink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class IRCConfiguration {

    private IRCSink ircSink;

    @Async
    @Scheduled(fixedDelay = 500)
    void refresh() {
        ircSink.sink();
    }

    @Autowired
    public void setIrcSink(IRCSink ircSink) {
        this.ircSink = ircSink;
    }
}
