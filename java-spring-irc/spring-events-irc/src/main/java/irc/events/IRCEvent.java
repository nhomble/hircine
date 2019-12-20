package irc.events;

import org.springframework.context.ApplicationEvent;

public abstract class IRCEvent extends ApplicationEvent {
    public IRCEvent(Object source) {
        super(source);
    }
}
