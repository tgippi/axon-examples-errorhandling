package com.tgippi.axon.rest;

import com.tgippi.axon.commands.ErrorCommand;
import com.tgippi.axon.commands.ExceptionType;
import org.axonframework.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @Autowired
    private Configuration configuration;

    @RequestMapping("/issueAnyException/{id}")
    public String issueAnyException(@PathVariable("id") String id) {
        configuration.commandGateway().sendAndWait(
            new ErrorCommand(id, ExceptionType.ANY_EXCEPTION)
        );
        return "Command sent";
    }

    @RequestMapping("/issueError/{id}")
    public String issueError(@PathVariable("id") String id) {
        configuration.commandGateway().sendAndWait(
                new ErrorCommand(id, ExceptionType.ERROR)
        );
        return "Command sent";
    }

    @RequestMapping("/issueInterruptedException/{id}")
    public String issueInterruptedException(@PathVariable("id") String id) {
        configuration.commandGateway().sendAndWait(
                new ErrorCommand(id, ExceptionType.INTERRUPTEDEXCEPTION)
        );
        return "Command sent";
    }

}
