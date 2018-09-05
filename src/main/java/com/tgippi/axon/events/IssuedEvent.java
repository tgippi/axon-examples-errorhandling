package com.tgippi.axon.events;

public class IssuedEvent {

    private final String id;
    private final Integer amount;

    public IssuedEvent(String id, Integer amount) {
        this.id = id;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public Integer getAmount() {
        return amount;
    }
}
