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

/**
 * Testklasse, welche die Fehlerzustände vom Axon-TEP versucht zu simulieren.
 * Voraussetzung: Der konfigurierte ListenerInvocationErrorHandler muss die Exception an den TEP durchreichen
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AxonTrackingEventProcessorFailureIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private Configuration axonConfiguration;

    /**
     * Stellt den Zustand her, dass der Tracking Event Processor in den Retry-Modus
     * gerät und das erzeugte Event immer wieder versucht abzuarbeiten.
     */
    @Test
    public void testAnyException() throws Exception {
        testError("/issueAnyException");
    }

    /**
     * Stellt den Zustand her, dass der Tracking Event Processor in den PAUSED_ERROR-State
     * gerät und aufhört zu arbeiten. Wird im Test jedoch wieder ins Leben gerufen und läuft dann
     * wieder an.
     */
    @Test
    public void testError() throws Exception {
        testError("/issueError");
    }

    /**
     * Stellt den Zustand her, dass der Tracking Event Processor in den SHUTDOWN-State
     * gerät und aufhört zu arbeiten. Wird im Test jedoch wieder ins Leben gerufen und läuft dann
     * in einen ungültigen Zustand: Alter Thread wartet auf Beenden, neuer Thread darf nichts machen,
     * da availableThreads == 0
     */
    @Test
    public void testInterruptedException() throws Exception {
        testError("/issueInterruptedException");
    }

    private void testError(String url) throws Exception {
        this.mvc.perform(get(url)).andExpect(status().isOk());

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
