package irc.events;

import irc.client.SpringIRCClient;

public class QuitIRCEvent extends ClientCommandIRCEvent {
    public QuitIRCEvent() {
        super(c -> c.getIRC().quit());
    }
}
