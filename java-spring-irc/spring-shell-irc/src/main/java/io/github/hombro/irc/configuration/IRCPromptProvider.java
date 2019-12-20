package io.github.hombro.irc.configuration;

import io.github.hombro.irc.commands.IRCCommands;
import io.github.hombro.irc.core.IRCClient;
import org.jline.utils.AttributedString;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class IRCPromptProvider implements PromptProvider {

    private final IRCCommands ircCommands;

    public IRCPromptProvider(IRCCommands ircCommands) {
        this.ircCommands = ircCommands;
    }

    @Override
    public AttributedString getPrompt() {
        if (ircCommands.ircClient().map(IRCClient::isConnected).orElse(false)) {
            return new AttributedString(ircCommands.ircClient().get().getChannel() + ">");
        } else {
            return new AttributedString("hircine>");
        }
    }
}
