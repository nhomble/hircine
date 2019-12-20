package irc.events;

public class LocalIRCEvent extends IRCEvent {

    private final String line;

    public LocalIRCEvent(String line) {
        super(line);
        this.line = line;
    }

    public String getLine(){
        return line;
    }
}
