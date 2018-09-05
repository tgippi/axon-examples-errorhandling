package com.tgippi.axon.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CardSummary {

    @Id
    private String id;

    @Column
    private Integer initialAmount;

    @Column
    private Integer remainingAmount;

    public CardSummary() {
    }

    public CardSummary(String id, Integer initialAmount, Integer remainingAmount) {
        this.id = id;
        this.initialAmount = initialAmount;
        this.remainingAmount = remainingAmount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getInitialAmount() {
        return initialAmount;
    }

    public void setInitialAmount(Integer initialAmount) {
        this.initialAmount = initialAmount;
    }

    public Integer getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(Integer remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public CardSummary deductAmount(Integer toBeDeducted) {
        return new CardSummary(id, initialAmount, remainingAmount - toBeDeducted);
    }

    @Override
    public String toString() {
        return "CardSummary{" +
                "id='" + id + '\'' +
                ", initialAmount=" + initialAmount +
                ", remainingAmount=" + remainingAmount +
                '}';
    }
}
