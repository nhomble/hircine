package irc.client;

import irc.events.UserInputIRCEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@AllArgsConstructor
@Slf4j
public class UserInputPublisher {

    private final ApplicationEventPublisher publisher;

    public void handleInput() {
        log.trace("handling input!");
        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNext()) {
                publisher.publishEvent(new UserInputIRCEvent(scanner.nextLine()));
            }
        }
    }
}
