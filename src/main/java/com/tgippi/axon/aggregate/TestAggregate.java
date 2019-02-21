package com.tgippi.axon.aggregate;

import com.tgippi.axon.commands.IssueErrorCommand;
import com.tgippi.axon.events.IssuedEvent;
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
    public TestAggregate(IssueErrorCommand cmd) {
        AggregateLifecycle.apply(new IssuedEvent(cmd.getId(), cmd.getType()));
    }

    @EventSourcingHandler
    public void on(IssuedEvent evt) {
        id = evt.getId();
    }

}
