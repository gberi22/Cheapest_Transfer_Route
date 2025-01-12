package com.example.cheapesttransferroute.service;

import com.example.cheapesttransferroute.model.Transfer;
import com.example.cheapesttransferroute.model.TransferRequest;
import com.example.cheapesttransferroute.repository.TransferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class TestTransferManagerService {
    private TransferManagerService transferManagerService;
    private TransferRepository transferRepository;

    @BeforeEach
    public void setUp() {
        transferRepository = new TransferRepository();
        transferManagerService = new TransferManagerService(transferRepository);
    }

    @Test
    public void testManageService() {
        TransferRequest transferRequest = new TransferRequest(15, Arrays.asList
            (
                new Transfer(5, 10),
                new Transfer(10, 20),
                new Transfer(3, 5),
                new Transfer(8, 15)
            )
        );
        transferManagerService.manageService(transferRequest);

        assertEquals(4, transferRepository.getTransfers().size());
        assertEquals(15, transferRepository.getMaxWeight());
    }

    @Test
    public void testPrepareService() {
        TransferRequest transferRequest = new TransferRequest(7, Arrays.asList
            (
                new Transfer(4, 10),
                new Transfer(3, 15),
                new Transfer(2, 5)
            )
        );

        transferManagerService.manageService(transferRequest);

        assertEquals(3, transferRepository.getTransfers().size());
        assertEquals(7, transferRepository.getMaxWeight());

        transferManagerService.prepareService();

        assertTrue( transferRepository.getTransfers().isEmpty());
        assertEquals(0, transferRepository.getMaxWeight());
    }

    @Test
    public void testIsAvailableTransferEmpty(){
        TransferRequest transferRequest = new TransferRequest(7, Arrays.asList
                (
                        new Transfer(4, 10),
                        new Transfer(3, 15),
                        new Transfer(2, 5)
                )
        );
        assertTrue(transferManagerService.isAvailableTransfersEmpty());

        transferManagerService.manageService(transferRequest);

        assertFalse(transferManagerService.isAvailableTransfersEmpty());

        transferManagerService.prepareService();

        assertTrue(transferManagerService.isAvailableTransfersEmpty());

    }
}
