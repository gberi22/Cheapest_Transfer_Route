package com.example.cheapesttransferroute.service;

import com.example.cheapesttransferroute.model.RouteResult;
import com.example.cheapesttransferroute.model.Transfer;
import com.example.cheapesttransferroute.repository.TransferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.cache.interceptor.LoggingCacheErrorHandler;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTransferServiceImpl {
    private TransferRepository transferRepository;
    private TransferServiceImpl transferServiceImpl;

    @BeforeEach
    public void setUp() {
        transferRepository = new TransferRepository();
        transferServiceImpl = new TransferServiceImpl(transferRepository);
    }

    @Test
    public void getMaximizedCostRoute_DefaultScenario() {
        transferRepository.addMaxWeight(15);
        transferRepository.addTransfer(new Transfer(5, 10));
        transferRepository.addTransfer(new Transfer(10, 20));
        transferRepository.addTransfer(new Transfer(8, 15));
        transferRepository.addTransfer(new Transfer(3, 5));

        RouteResult result = transferServiceImpl.getMaximizedCostRoute();

        assertEquals(30, result.getTotalCost());
        assertEquals(15, result.getTotalWeight());
        assertEquals(2, result.getSelectedTransfers().size());

    }

    @Test
    public void testGetMaximizedCostRoute_ExceededWeight() {
        transferRepository.addMaxWeight(15);
        transferRepository.addTransfer(new Transfer(50, 100));
        transferRepository.addTransfer(new Transfer(100, 29));
        transferRepository.addTransfer(new Transfer(91, 20));

        RouteResult result = transferServiceImpl.getMaximizedCostRoute();

        assertEquals(0, result.getTotalCost());
        assertEquals(0, result.getTotalWeight());
        assertEquals(0, result.getSelectedTransfers().size());
    }

    @Test
    public void testGetMaximizedCostRoute_ZeroMaxWeight(){
        transferRepository.addMaxWeight(0);
        transferRepository.addTransfer(new Transfer(5, 10));
        transferRepository.addTransfer(new Transfer(10, 20));
        transferRepository.addTransfer(new Transfer(8, 15));
        transferRepository.addTransfer(new Transfer(3, 5));

        RouteResult result = transferServiceImpl.getMaximizedCostRoute();
        assertEquals(0, result.getTotalCost());
        assertEquals(0, result.getTotalWeight());
        assertTrue(result.getSelectedTransfers().isEmpty());
    }

    @Test
    public void testGetMaximizedCostRoute_ZeroWeightTransfers(){
        transferRepository.addMaxWeight(0);
        transferRepository.addTransfer(new Transfer(0, 10));
        transferRepository.addTransfer(new Transfer(10, 20));
        transferRepository.addTransfer(new Transfer(0, 15));
        transferRepository.addTransfer(new Transfer(3, 5));

        RouteResult result = transferServiceImpl.getMaximizedCostRoute();
        assertEquals(25, result.getTotalCost());
        assertEquals(0, result.getTotalWeight());
        assertEquals(2, result.getSelectedTransfers().size());
    }

    @Test
    public void testGetMaximizedCostRoute_ZeroCostTransfers(){
        transferRepository.addMaxWeight(10);
        transferRepository.addTransfer(new Transfer(0, 0));
        transferRepository.addTransfer(new Transfer(10, 0));
        transferRepository.addTransfer(new Transfer(20, 0));
        transferRepository.addTransfer(new Transfer(3, 0));

        RouteResult result = transferServiceImpl.getMaximizedCostRoute();
        assertEquals(0, result.getTotalCost());
        assertEquals(0, result.getTotalWeight());
        assertTrue(result.getSelectedTransfers().isEmpty());
    }

    @Test
    public void testGetMaximizedCostRoute_LargeInputs(){
        transferRepository.addMaxWeight(3000);
        transferRepository.addTransfer(new Transfer(1000, 10000));
        transferRepository.addTransfer(new Transfer(2000, 20000));
        transferRepository.addTransfer(new Transfer(3000, 30000));
        transferRepository.addTransfer(new Transfer(0, 10));

        RouteResult result = transferServiceImpl.getMaximizedCostRoute();
        assertEquals(30010, result.getTotalCost());
        assertEquals(3000, result.getTotalWeight());
        assertEquals(3, result.getSelectedTransfers().size());
    }

}