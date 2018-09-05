package com.tgippi.axon.commands;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class RedeemCommand {

    @TargetAggregateIdentifier
    private final String id;

    private final Integer amount;

    public String getId() {
        return id;
    }

    public Integer getAmount() {
        return amount;
    }

    public RedeemCommand(String id, Integer amount) {
        this.id = id;
        this.amount = amount;
    }
}
