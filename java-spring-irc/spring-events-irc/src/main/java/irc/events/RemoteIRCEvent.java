package irc.events;

public class RemoteIRCEvent extends IRCEvent {

    private final String remoteMessage;

    public RemoteIRCEvent(String remote) {
        super(remote);
        this.remoteMessage = remote;
    }

    public String getRemoteMessage() {
        return remoteMessage;
    }
}
