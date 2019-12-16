package io.github.hombro.irc.client;

import io.github.hombro.irc.events.IRCEvent;
import io.github.hombro.irc.events.QuitIRCEvent;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ConfigurableApplicationContext;
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
