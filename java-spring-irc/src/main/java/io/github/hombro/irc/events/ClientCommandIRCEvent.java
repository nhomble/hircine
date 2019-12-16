package io.github.hombro.irc.events;

import io.github.hombro.irc.client.IRCClient;
import io.github.hombro.irc.client.UserInputProcessor;

import java.util.function.Consumer;

public class ClientCommandIRCEvent extends IRCEvent {

    private final Consumer<IRCClient> consumer;

    public ClientCommandIRCEvent(Consumer<IRCClient> consumer) {
        super(consumer);
        this.consumer = consumer;
    }

    public void handle(IRCClient ircClient){
        consumer.accept(ircClient);
    }
}
