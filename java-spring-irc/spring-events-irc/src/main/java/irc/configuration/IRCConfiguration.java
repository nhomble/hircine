package irc.configuration;

import io.github.hombro.irc.core.SocketIRCClient;
import irc.client.SpringIRCClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(IRCSettings.class)
public class IRCConfiguration {

    @Bean
    public SpringIRCClient ircClient(IRCSettings ircSettings) {
        SocketIRCClient client = new SocketIRCClient(
                ircSettings.getServer(),
                ircSettings.getPort(),
                ircSettings.getChannel(),
                ircSettings.getUsername(),
                ircSettings.getDefaultNickName()
        );
        return new SpringIRCClient(client);
    }

}
