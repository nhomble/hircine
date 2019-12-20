package io.github.hombro.irc.client;

import io.github.hombro.irc.commands.IRCCommands;
import io.github.hombro.irc.core.IRCClient;
import org.jline.terminal.Terminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class IRCSink {
    private final IRCCommands ircCommands;
    private Terminal terminal;

    public IRCSink(IRCCommands ircCommands) {
        this.ircCommands = ircCommands;
    }

    @Autowired
    @Lazy
    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    @Async
    public void sink() {
        if (ircCommands.ircClient().map(IRCClient::isConnected).orElse(false)) {
            ircCommands.ircClient().get().check().ifPresent(s -> {
                terminal.writer().println(ircCommands.ircClient().get().getChannel() + "> " + s);
                terminal.flush();
                sink();
            });
        }
    }
}
