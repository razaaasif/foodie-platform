package com.foodie.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Created on 29/05/25.
 *
 * @author : aasif.raza
 */
@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "kafkaExecutor")
    public Executor kafkaExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("KafkaSender-");
        executor.initialize();
        return executor;
    }
}