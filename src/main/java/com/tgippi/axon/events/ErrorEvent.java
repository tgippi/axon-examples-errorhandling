package com.tgippi.axon.events;

import com.tgippi.axon.commands.ExceptionType;

public class ErrorEvent {

    private final String id;
    private final ExceptionType type;

    public ErrorEvent(String id, ExceptionType type) {
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
