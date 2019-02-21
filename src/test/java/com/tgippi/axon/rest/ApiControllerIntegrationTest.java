package com.tgippi.axon.rest;

import org.axonframework.config.Configuration;
import org.axonframework.eventhandling.EventProcessor;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApiControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private Configuration axonConfiguration;

    @Test
    public void testIssue() throws Exception {
        this.mvc.perform(get("/issue")).andExpect(status().isOk());

        Optional<EventProcessor> eventProcessor = axonConfiguration.eventProcessingConfiguration()
                .eventProcessor("cardsummary");

        while(true) {
            if (eventProcessor.isPresent() && eventProcessor.get() instanceof TrackingEventProcessor) {
                checkAndRestartTEP((TrackingEventProcessor) eventProcessor.get());
            }
            Thread.sleep(10000);
        }
    }

    private void checkAndRestartTEP(TrackingEventProcessor trackingEventProcessor) {
        System.out.println("Running: " + trackingEventProcessor.isRunning() +
                " ; Error: " + trackingEventProcessor.isError());

        if (!trackingEventProcessor.isRunning()) {
            System.out.println("Starte TrackingEventProcessor Neu!");
            trackingEventProcessor.start();
        }
    }
}
