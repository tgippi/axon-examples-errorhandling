package com.tgippi.axon.projection;

import com.tgippi.axon.events.IssuedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("cardsummary")
public class CardSummaryProjection {

    @EventHandler
    public void on(IssuedEvent evt) throws Throwable {
        throw new Error("");

        /*throw new InterruptedException("Interrupt!");*/
        /*throw new RuntimeException("Blablabla");*/
    }
}
