package com.example.cheapesttransferroute.controller;

import com.example.cheapesttransferroute.json.JSONFileReader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class TestTransferControllerJSONInput {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testChosenRouteDefault() throws Exception {
        String validRequestJSON = JSONFileReader.loadJSON("valid_request.json");
        mockMvc.perform(post("/api/transfers/inputRoutes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(validRequestJSON))
            .andExpect(status().isOk());
    }

    @Test
    public void testGetMaximizedCostRouteDefault() throws Exception {
        String validRequestJSON = JSONFileReader.loadJSON("valid_request.json");
        mockMvc.perform(post("/api/transfers/inputRoutes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(validRequestJSON))
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
        String emptyTransferJSON = JSONFileReader.loadJSON("empty_transfers.json");
        mockMvc.perform(post("/api/transfers/inputRoutes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(emptyTransferJSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.availableTransfers").value("Available transfers list cannot be empty"));
    }

    @Test
    void testPostInputRoutesJSONInvalidInp() throws Exception {
        String invalidRequestJSON = JSONFileReader.loadJSON("invalid_request.json");

        mockMvc.perform(post("/api/transfers/inputRoutes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(invalidRequestJSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value("Invalid input format"))
            .andExpect(jsonPath("$.availableTransfers.[0].weight").value("Weight must be at least 1"))
            .andExpect(jsonPath("$.availableTransfers[0].cost").value("Cost must be at least 0"))
            .andExpect(jsonPath("$.availableTransfers[1].cost").value("Invalid input format"));

    }

    @Test
    void testPostInputRoutesJSONOutBoundsInp() throws Exception {
        String invalidRequestJSON = JSONFileReader.loadJSON("out_bounds.json");
        mockMvc.perform(post("/api/transfers/inputRoutes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(invalidRequestJSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.maxWeight").value("Maximum weight must be at least 1"))
            .andExpect(jsonPath("$.availableTransfers[0].weight").value("Weight must be at least 1"))
            .andExpect(jsonPath("$.availableTransfers[0].cost").value("Cost must be at least 0"))
            .andExpect(jsonPath("$.availableTransfers[1].weight").value("Weight must be at least 1"));
    }

}
