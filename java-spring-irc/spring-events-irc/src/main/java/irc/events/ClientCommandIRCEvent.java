package irc.events;

import irc.client.SpringIRCClient;

import java.util.function.Consumer;

public class ClientCommandIRCEvent extends IRCEvent {

    private final Consumer<SpringIRCClient> consumer;

    public ClientCommandIRCEvent(Consumer<SpringIRCClient> consumer) {
        super(consumer);
        this.consumer = consumer;
    }

    public void handle(SpringIRCClient springIrcClient){
        consumer.accept(springIrcClient);
    }
}
