package com.tgippi.axon.dto;

import com.tgippi.axon.model.CardSummary;

import java.util.Collection;

public class CardSummaries {

    private Collection<CardSummary> cardSummaries;

    public CardSummaries(Collection<CardSummary> cardSummaries) {
        this.cardSummaries = cardSummaries;
    }

    public Collection<CardSummary> getCardSummaries() {
        return cardSummaries;
    }

    public void setCardSummaries(Collection<CardSummary> cardSummaries) {
        this.cardSummaries = cardSummaries;
    }
}
