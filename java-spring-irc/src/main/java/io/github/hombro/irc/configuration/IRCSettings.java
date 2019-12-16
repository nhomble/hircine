package io.github.hombro.irc.configuration;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Optional;

/**
 * https://tools.ietf.org/html/rfc2812
 */
@Data
@Validated
@ConfigurationProperties("irc")
public class IRCSettings {

    /**
     * irc server
     * <p>
     * 1.1 Servers
     */
    @Size(max = 63)
    private String server;

    /**
     * irc server port
     */
    private int port;

    /**
     * irc channel
     * <p>
     * 1.3 Channels
     */
    @Size(max = 50)
    @NotBlank
    private String channel;

    /**
     * nickname
     * <p>
     * 1.2.1 Users
     */
    @Size(max = 9)
    private String username;

    private String defaultNickName;

    @AssertTrue(message = "valid channel prefix")
    public boolean channelHasValidPrefix() {
        for (char c : new char[]{'&', '#', '+', '!'}) {
            if (!channel.isEmpty() && channel.charAt(0) == c) {
                return true;
            }
        }
        return false;
    }
}
