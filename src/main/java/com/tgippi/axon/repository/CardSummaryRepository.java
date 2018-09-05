package com.tgippi.axon.repository;

import com.tgippi.axon.model.CardSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardSummaryRepository extends JpaRepository<CardSummary, String> {
}
