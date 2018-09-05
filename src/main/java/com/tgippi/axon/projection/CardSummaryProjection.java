package com.tgippi.axon.projection;

import com.tgippi.axon.dto.CardSummaries;
import com.tgippi.axon.events.IssuedEvent;
import com.tgippi.axon.events.RedeemedEvent;
import com.tgippi.axon.model.CardSummary;
import com.tgippi.axon.query.FetchCardSummariesQuery;
import com.tgippi.axon.repository.CardSummaryRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CardSummaryProjection {

    @Autowired
    private CardSummaryRepository repository;

    @EventHandler
    public void on(IssuedEvent evt) {
        CardSummary cardSummary = new CardSummary(evt.getId(), evt.getAmount(), evt.getAmount());
        repository.save(cardSummary);
    }

    @EventHandler
    public void on(RedeemedEvent evt) {
        repository.findAll().stream()
                .filter(cs -> evt.getId().equals(cs.getId()))
                .findFirst()
                .ifPresent(cardSummary -> {
                    CardSummary updatedCardSummary = cardSummary.deductAmount(evt.getAmount());
                    repository.delete(cardSummary);
                    repository.save(updatedCardSummary);
                });
    }

    @QueryHandler
    public CardSummaries fetch(FetchCardSummariesQuery query) {
        return new CardSummaries(repository.findAll().stream()
                .skip(query.getOffset())
                .limit(query.getSize())
                .collect(Collectors.toList()));
    }
}