package com.example.cheapesttransferroute.controller;

import com.example.cheapesttransferroute.model.Transfer;
import com.example.cheapesttransferroute.model.TransferRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.awt.*;
import java.util.Arrays;

import static java.util.Collections.emptyList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TestTransferController {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testChosenRouteBuild() throws Exception {
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
    public void testChosenRoute_EmptyTransfers() throws Exception {
        TransferRequest request = new TransferRequest(15, emptyList());
        mockMvc.perform(post("/api/transfers/inputRoutes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk());
    }

    @Test
    public void testGetMaximizedCostRoute_NoTransfers() throws Exception {
        mockMvc.perform(get("/api/transfers/getRoute"))
            .andExpect(status().isNotFound())
            .andExpect(content().string(""));
    }


    @Test
    public void testGetMaximizedCostRoute_Default() throws Exception {
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


//    @Test
//    public void testChosenRoute_JSONInputBuild() throws Exception {
//
//    }


}
