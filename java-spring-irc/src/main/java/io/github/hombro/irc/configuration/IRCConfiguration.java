package io.github.hombro.irc.configuration;

import io.github.hombro.irc.client.IRCClient;
import io.github.hombro.irc.client.IRCServerEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableConfigurationProperties(IRCSettings.class)
public class IRCConfiguration {

    @Bean
    public IRCClient ircClient(IRCSettings ircSettings){
        return new IRCClient(ircSettings.getServer(),
                ircSettings.getPort(),
                ircSettings.getChannel(),
                ircSettings.getUsername(),
                ircSettings.getDefaultNickName()
        );
    }

}
