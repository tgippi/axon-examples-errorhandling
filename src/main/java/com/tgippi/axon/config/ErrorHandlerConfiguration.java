package com.tgippi.axon.config;

import org.axonframework.eventhandling.ListenerInvocationErrorHandler;
import org.axonframework.eventhandling.PropagatingErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ErrorHandlerConfiguration {

    @Bean
    public ListenerInvocationErrorHandler errors() {
        return PropagatingErrorHandler.instance();
    }

}
