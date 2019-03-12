package com.tgippi.axon.aggregate;

import com.tgippi.axon.commands.ErrorCommand;
import com.tgippi.axon.events.ErrorEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class TestAggregate {

    @AggregateIdentifier
    private String id;

    public TestAggregate() {
    }

    @CommandHandler
    public TestAggregate(ErrorCommand cmd) {
        AggregateLifecycle.apply(new ErrorEvent(cmd.getId(), cmd.getType()));
    }

    @EventSourcingHandler
    public void on(ErrorEvent evt) {
        id = evt.getId();
    }
}
