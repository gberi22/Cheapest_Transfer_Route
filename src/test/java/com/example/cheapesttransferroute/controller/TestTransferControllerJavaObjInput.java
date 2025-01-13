package com.example.cheapesttransferroute.controller;

import com.example.cheapesttransferroute.model.Transfer;
import com.example.cheapesttransferroute.model.TransferRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TestTransferControllerJavaObjInput {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testChosenRouteDefault() throws Exception {
        TransferRequest request = new TransferRequest(15, Arrays.asList
            (
                new Transfer(12, 53),
                new Transfer(10, 25),
                new Transfer(5, 9),
                new Transfer(14, 15)
            )
        );
        mockMvc.perform(post("/api/transfers/inputRoutes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk());
    }

    @Test
    public void testGetMaximizedCostRouteNoTransfers() throws Exception {
        mockMvc.perform(get("/api/transfers/getRoute"))
            .andExpect(status().isNotFound())
            .andExpect(content().string(""));
    }

    @Test
    public void testGetMaximizedCostRouteDefault() throws Exception {
        TransferRequest request = new TransferRequest(15, Arrays.asList
            (
                new Transfer(5, 10),
                new Transfer(10, 20),
                new Transfer(3, 5),
                new Transfer(8, 15)
            )
        );

        mockMvc.perform(post("/api/transfers/inputRoutes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk());

        mockMvc.perform(get("/api/transfers/getRoute"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.totalCost").value(30))
            .andExpect(jsonPath("$.totalWeight").value(15))
            .andExpect(jsonPath("$.selectedTransfers").isArray())
            .andExpect(jsonPath("$.selectedTransfers.size()").value(2)
        );
    }

    @Test
    public void testChosenRouteEmptyTransfers() throws Exception {
        TransferRequest request = new TransferRequest(15, Collections.emptyList());
        mockMvc.perform(post("/api/transfers/inputRoutes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.availableTransfers").value("Available transfers list cannot be empty"));
    }


    @Test
    public void testChosenRouteOutBoundsMaxWeightInp() throws Exception {
        TransferRequest invalidRequest = new TransferRequest(0, Arrays.asList
            (
                new Transfer(5, 10),
                new Transfer(10, 20)
            )
        );
        mockMvc.perform(post("/api/transfers/inputRoutes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invalidRequest)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.maxWeight").value("Maximum weight must be at least 1"));
    }

    @Test
    public void testChosenRouteOutBoundsWeightInp() throws Exception {
        TransferRequest invalidRequest = new TransferRequest(10, Arrays.asList
            (
                new Transfer(-5, 10),
                new Transfer(10, 20)
            )
        );
        mockMvc.perform(post("/api/transfers/inputRoutes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invalidRequest)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.weight").value("Weight must be at least 1"));
    }

    @Test
    public void testChosenRouteOutBoundsCostInp() throws Exception {
        TransferRequest invalidRequest = new TransferRequest(10, Arrays.asList
            (
                new Transfer(5, 10),
                new Transfer(10, -20)
            )
        );
        mockMvc.perform(post("/api/transfers/inputRoutes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invalidRequest)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.cost").value("Cost must be at least 1"));
    }

}
