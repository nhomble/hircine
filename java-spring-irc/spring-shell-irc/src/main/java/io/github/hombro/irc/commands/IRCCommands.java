package io.github.hombro.irc.commands;

import io.github.hombro.irc.core.IRCClient;
import io.github.hombro.irc.core.SocketIRCClient;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.io.IOException;
import java.util.Optional;

@ShellComponent
@ShellCommandGroup
public class IRCCommands {

    private Optional<IRCClient> ircClient = Optional.empty();

    @ShellMethod("connect to IRC destination")
    public void connectTo(String server, int port, String channel, String username, String nickName) throws IOException {
        ircClient = Optional.of(new SocketIRCClient(server, port, channel, username, nickName));
        ircClient.get().login();
    }

    @ShellComponent
    @ShellCommandGroup
    public class ConnectedIRCCommands {
        @ShellMethodAvailability
        public Availability ircAvailable() {
            return ircClient.map(i -> i.isConnected() ? Availability.available()
                    : Availability.unavailable("irc client is not connected"))
                    .orElse(Availability.unavailable("irc client not created yet"));
        }

        @ShellMethod("message")
        void message(String... words){
            assume().sendMessage(String.join(" ", words));
        }

        @ShellMethod("logout out from IRC server")
        void logout() {
            assume().quit();
        }
    }

    public Optional<IRCClient> ircClient() {
        return ircClient;
    }

    public IRCClient assume() {
        return ircClient.orElseThrow(() -> new RuntimeException("didn't connect"));
    }
}
