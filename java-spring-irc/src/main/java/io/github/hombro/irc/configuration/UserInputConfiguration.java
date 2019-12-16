package io.github.hombro.irc.configuration;

import io.github.hombro.irc.client.UserInputPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TaskExecutorFactoryBean;

@Import({
        ScheduleConfiguration.class,
        AsyncConfiguration.class
})
@Configuration
public class UserInputConfiguration {

    private UserInputPublisher publisher;

    @Bean
    public TaskExecutor userInputExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(1);
        executor.setThreadNamePrefix("user-input-tasl");
        executor.setAwaitTerminationSeconds(1);
        return executor;
    }

    @Async("userInputExecutor")
    @Scheduled(initialDelay = 1000, fixedDelay = 500)
    void handleInput() {
        publisher.handleInput();
    }

    @Autowired
    void setPublisher(UserInputPublisher publisher) {
        this.publisher = publisher;
    }
}
