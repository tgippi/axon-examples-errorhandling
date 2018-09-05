package com.tgippi.axon.projection;

import com.tgippi.axon.events.IssuedEvent;
import com.tgippi.axon.events.RedeemedEvent;
import com.tgippi.axon.model.CardSummary;
import com.tgippi.axon.query.FetchCardSummariesQuery;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Component
public class CardSummaryProjection {

    private final List<CardSummary> cardSummaries = new CopyOnWriteArrayList();

    @EventHandler
    public void on(IssuedEvent evt) {
        CardSummary cardSummary = new CardSummary(evt.getId(), evt.getAmount(), evt.getAmount());
        cardSummaries.add(cardSummary);
    }

    @EventHandler
    public void on(RedeemedEvent evt) {
        cardSummaries.stream()
                .filter(cs -> evt.getId().equals(cs.getId()))
                .findFirst()
                .ifPresent(cardSummary -> {
                    CardSummary updatedCardSummary = cardSummary.deductAmount(evt.getAmount());
                    cardSummaries.remove(cardSummary);
                    cardSummaries.add(updatedCardSummary);
                });
    }

    @QueryHandler
    public List<CardSummary> fetch(FetchCardSummariesQuery query) {
        return cardSummaries.stream()
                .skip(query.getOffset())
                .limit(query.getSize())
                .collect(Collectors.toList());
    }
}