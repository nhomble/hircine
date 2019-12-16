package io.github.hombro.irc.events;

import org.springframework.context.ApplicationEvent;

public class UserInputIRCEvent extends IRCEvent {

    private final String line;

    public UserInputIRCEvent(String line) {
        super(line);
        this.line = line;
    }

    public String getLine() {
        return line;
    }
}
