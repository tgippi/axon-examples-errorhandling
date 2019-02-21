package com.tgippi.axon.projection;

import com.tgippi.axon.commands.ExceptionType;
import com.tgippi.axon.events.IssuedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("test")
public class TestProjection {

    @EventHandler
    public void on(IssuedEvent evt) throws Throwable {
        if (ExceptionType.ANY_EXCEPTION.equals(evt.getType())) {
            throw new RuntimeException("Blablabla");
        } else if (ExceptionType.ERROR.equals(evt.getType())) {
            throw new Error("");
        } else if (ExceptionType.INTERRUPTEDEXCEPTION.equals(evt.getType())) {
            throw new InterruptedException("Interrupt!");
        }
    }
}
