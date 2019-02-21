package com.tgippi.axon.config;

import org.axonframework.eventhandling.EventListener;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.ListenerInvocationErrorHandler;
import org.axonframework.eventhandling.LoggingErrorHandler;
import org.axonframework.eventhandling.PropagatingErrorHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

@Configuration
public class CustomErrorHandler  {

    @Bean
    public ListenerInvocationErrorHandler errors() {
        // return new LoggingErrorHandler();
        // return new LoggingPropagatingErrorHandler();
        return PropagatingErrorHandler.instance();
    }

    class LoggingPropagatingErrorHandler implements ListenerInvocationErrorHandler {
        @Override
        public void onError(Exception exception, EventMessage<?> event, EventListener eventListener) throws Exception {
            if (exception instanceof InterruptedException) {
                throw new CustomException(exception);
            }
            throw exception;
        }
    }

    class CustomException extends Exception {
        CustomException(Exception cause) {
            super(cause);
        }
    }
}
