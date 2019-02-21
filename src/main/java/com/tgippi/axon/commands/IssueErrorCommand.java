package com.tgippi.axon.commands;

public class IssueErrorCommand {

    private final String id;

    private final ExceptionType type;

    public IssueErrorCommand(String id, ExceptionType type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public ExceptionType getType() { return type; }


}
