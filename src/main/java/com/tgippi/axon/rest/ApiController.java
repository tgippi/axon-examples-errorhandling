package com.tgippi.axon.rest;

import com.tgippi.axon.commands.IssueCommand;
import com.tgippi.axon.commands.RedeemCommand;
import com.tgippi.axon.dto.CardSummaries;
import com.tgippi.axon.query.FetchCardSummariesQuery;
import org.axonframework.config.Configuration;
import org.axonframework.queryhandling.responsetypes.ResponseTypes;
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

    @RequestMapping("/read")
    public CardSummaries readSomething() throws Exception {
        return configuration.queryGateway().query(
                new FetchCardSummariesQuery(2, 0),
                ResponseTypes.instanceOf(CardSummaries.class))
                .get();
    }

    @RequestMapping("/issue")
    public String issue() {
        configuration.commandGateway().sendAndWait(
            new IssueCommand(UUID.randomUUID().toString(), 100)
        );
        return "Issue Command abgeschickt";
    }

    @RequestMapping("/redeem")
    public String redeem() {
        configuration.commandGateway().sendAndWait(
            new RedeemCommand("gc1", 50)
        );
        return "Command abgeschickt";
    }

}
