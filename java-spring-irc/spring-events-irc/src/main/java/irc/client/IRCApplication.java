package irc.client;

import irc.events.QuitIRCEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class IRCApplication {

    private final CountDownLatch latch = new CountDownLatch(1);

    public IRCApplication() {
        Runtime.getRuntime().addShutdownHook(new Thread(latch::countDown));
    }

    public void waitForCompletion() throws InterruptedException {
        latch.await();
    }

    @EventListener
    public void onApplicationEvent(QuitIRCEvent event) {
        latch.countDown();
        System.exit(0);
    }
}
