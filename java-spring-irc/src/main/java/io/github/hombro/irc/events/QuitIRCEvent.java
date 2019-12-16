package io.github.hombro.irc.events;

import io.github.hombro.irc.client.IRCClient;

import java.util.function.Consumer;

public class QuitIRCEvent extends ClientCommandIRCEvent {
    public QuitIRCEvent() {
        super(IRCClient::quit);
    }
}
