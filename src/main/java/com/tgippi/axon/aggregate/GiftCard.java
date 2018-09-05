package com.tgippi.axon.aggregate;

import com.tgippi.axon.commands.IssueCommand;
import com.tgippi.axon.commands.RedeemCommand;
import com.tgippi.axon.events.IssuedEvent;
import com.tgippi.axon.events.RedeemedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class GiftCard {

    @AggregateIdentifier
    private String id;

    private int remainingValue;

    public GiftCard() {
    }

    @CommandHandler
    public GiftCard(IssueCommand cmd) {
        if(cmd.getAmount() <= 0) throw new IllegalArgumentException("amount <= 0");
        AggregateLifecycle.apply(new IssuedEvent(cmd.getId(), cmd.getAmount()));
    }

    @EventSourcingHandler
    public void on(IssuedEvent evt) {
        id = evt.getId();
        remainingValue = evt.getAmount();
    }

    @CommandHandler
    public void handle(RedeemCommand cmd) {
        if(cmd.getAmount() <= 0) throw new IllegalArgumentException("amount <= 0");
        if(cmd.getAmount() > remainingValue) throw new IllegalStateException("amount > remaining value");
        AggregateLifecycle.apply(new RedeemedEvent(id, cmd.getAmount()));
    }

    @EventSourcingHandler
    public void on(RedeemedEvent evt) {
        remainingValue -= evt.getAmount();
    }
}
