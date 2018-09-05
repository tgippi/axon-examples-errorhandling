package com.tgippi.axon.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApiControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testIndex() throws Exception {
        this.mvc.perform(get("/")).andExpect(status().isOk())
                .andExpect(content().string("Greetings from Spring Boot!"));
    }

    @Test
    public void testIssue() throws Exception {
        this.mvc.perform(get("/issue")).andExpect(status().isOk());
        this.mvc.perform(get("/read")).andExpect(status().isOk())
                .andExpect(content().string("{\"cardSummaries\":[{\"id\":\"gc1\",\"initialAmount\":100,\"remainingAmount\":100}]}"));
    }

    @Test
    public void testRedeem() throws Exception {
        this.mvc.perform(get("/redeem")).andExpect(status().isOk());
        this.mvc.perform(get("/read")).andExpect(status().isOk())
                .andExpect(content().string("{\"cardSummaries\":[{\"id\":\"gc1\",\"initialAmount\":100,\"remainingAmount\":50}]}"));
    }
}
