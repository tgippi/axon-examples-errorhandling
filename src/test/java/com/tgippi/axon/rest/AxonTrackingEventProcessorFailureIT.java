package com.tgippi.axon.rest;

import org.axonframework.config.Configuration;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This is a test, which simulates the different error states of a tracking event processor.
 * We assume, that the PropagatingErrorHandler has been configured for exception handling.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AxonTrackingEventProcessorFailureIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private Configuration axonConfiguration;

    private String aggregateId;

    @Before
    public void before() {
        aggregateId = UUID.randomUUID().toString();
    }

    /**
     * Throws any kind of exception and start the retry mechanism of the TEP
     */
    @Test
    public void testAnyException() throws Exception {
        callUrl("/issueAnyException");

        restartTEPIfNotRunning();
        checkTEPIsRunning();
    }

    /**
     * Throws an Error. The TEP is going into the PAUSED_ERROR-State and stops processing.
     * When we start the tracking event processor, it will start processing events.
     */
    @Test
    public void testError() throws Exception {
        callUrl("/issueError");

        checkTEPIsNotRunning();
        restartTEPIfNotRunning();
        checkTEPIsRunning();
    }

    /**
     * Throws an InterrupedException. The TEP is going into the SHUTDOWN-State and stops processing.
     * When we start the tracking event processor by using the API, it won't start processing events, altough it is
     * going into the STARTED-State
     *
     * It seems that there is still a thread running, but staying in the shutDown() method.
     * The newly created thread isn't able to process events, because the availableThreads attribute is 0
     */
    @Test
    public void testInterruptedException() throws Exception {
        // Throw InterruptedException in EventHandler
        callUrl("/issueInterruptedException");

        // Check that TEP isn't in the running state
        checkTEPIsNotRunning();
        // Available threads now is 0
        checkAvailableThreads(0);

        // TEP is going into the STARTED-State but has no available threads
        restartTEPIfNotRunning();
        checkTEPIsRunning();
    }

    private void restartTEPIfNotRunning() {
        TrackingEventProcessor tep = getTEP();
        if(!tep.isRunning()) {
            System.out.println("TEP is not running. Start!");
            tep.start();
        }
    }

    private void checkTEPIsRunning() {
        assertTrue("TEP is not running", getTEP().isRunning());
    }

    private void checkTEPIsNotRunning() {
        assertFalse("TEP is running", getTEP().isRunning());
    }

    private void checkAvailableThreads(int expected) {
        assertEquals(expected, getTEP().availableProcessorThreads());
    }

    private void callUrl(String url) throws Exception {
        this.mvc.perform(get(url + "/" + aggregateId)).andExpect(status().isOk());
        Thread.sleep(250);
    }

    private TrackingEventProcessor getTEP() {
        return (TrackingEventProcessor) axonConfiguration
                    .eventProcessingConfiguration()
                    .eventProcessor("test").get();
    }

}
