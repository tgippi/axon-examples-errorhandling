package com.tgippi.axon.rest;

import com.tgippi.axon.commands.ExceptionType;
import com.tgippi.axon.commands.IssueErrorCommand;
import org.axonframework.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ApiController {

    @Autowired
    private Configuration configuration;

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/issueAnyException")
    public String issueAnyException() {
        configuration.commandGateway().sendAndWait(
            new IssueErrorCommand(UUID.randomUUID().toString(), ExceptionType.ANY_EXCEPTION)
        );
        return "Issue Command abgeschickt";
    }

    @RequestMapping("/issueError")
    public String issueError() {
        configuration.commandGateway().sendAndWait(
                new IssueErrorCommand(UUID.randomUUID().toString(), ExceptionType.ERROR)
        );
        return "Issue Command abgeschickt";
    }

    @RequestMapping("/issueInterruptedException")
    public String issueInterruptedException() {
        configuration.commandGateway().sendAndWait(
                new IssueErrorCommand(UUID.randomUUID().toString(), ExceptionType.INTERRUPTEDEXCEPTION)
        );
        return "Issue Command abgeschickt";
    }

}
