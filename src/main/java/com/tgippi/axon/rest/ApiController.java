package com.tgippi.axon.rest;

import com.tgippi.axon.commands.IssueCommand;
import com.tgippi.axon.model.CardSummary;
import com.tgippi.axon.query.FetchCardSummariesQuery;
import org.axonframework.config.Configuration;
import org.axonframework.queryhandling.responsetypes.ResponseTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiController {

    @Autowired
    private Configuration configuration;

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/read")
    public String readSomething() throws Exception {
        List<CardSummary> cardSummaries = configuration.queryGateway().query(
                new FetchCardSummariesQuery(2, 0),
                ResponseTypes.multipleInstancesOf(CardSummary.class))
                .get();

        StringBuilder sb = new StringBuilder();
        cardSummaries.forEach((CardSummary summary) ->
            sb.append(summary.toString()).append("\n")
        );
        return sb.toString();
    }

    @RequestMapping("/add")
    public String postSomething() {
        configuration.commandGateway().sendAndWait(
            new IssueCommand("gc1", 100)
        );
        return "Command abgeschickt";
    }

}
