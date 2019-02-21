package com.tgippi.axon.events;

import com.tgippi.axon.commands.ExceptionType;

public class IssuedEvent {

    private final String id;
    private final ExceptionType type;

    public IssuedEvent(String id, ExceptionType type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public ExceptionType getType() {
        return type;
    }
}
