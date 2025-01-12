package com.example.cheapesttransferroute.repository;

import com.example.cheapesttransferroute.model.Transfer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTransferRepository {
    private TransferRepository transferRepository;

    @BeforeEach
    public void setUp() {
        transferRepository = new TransferRepository();
    }

    @Test
    public void testAddTransfer() {
        List<Transfer> transfers = transferRepository.getTransfers();
        assertEquals(0, transfers.size());

        Transfer transfer1 = new Transfer(1,20);
        transferRepository.addTransfer(transfer1);

        assertEquals(1, transfers.size());
        assertEquals(transfer1, transfers.get(0));

        Transfer transfer2 = new Transfer(5,15);
        transferRepository.addTransfer(transfer2);

        assertEquals(2, transfers.size());
        assertEquals(transfer2, transfers.get(1));
    }

    @Test
    public void testAddMaxWeight() {
        int maxWeight1 = 7;
        transferRepository.addMaxWeight(maxWeight1);
        assertEquals(maxWeight1, transferRepository.getMaxWeight());

        int maxWeight2 = 40;
        transferRepository.addMaxWeight(maxWeight2);
        assertEquals(maxWeight2, transferRepository.getMaxWeight());
    }

    @Test
    public void testClearRepositoryInformation(){
        Transfer transfer1 = new Transfer(5, 10);
        Transfer transfer2 = new Transfer(10, 20);

        transferRepository.addTransfer(transfer1);
        transferRepository.addTransfer(transfer2);

        int maxWeight = 30;

        transferRepository.addMaxWeight(maxWeight);

        assertEquals(2, transferRepository.getTransfers().size());
        assertEquals(maxWeight, transferRepository.getMaxWeight());

        transferRepository.clearRepositoryInformation();

        assertEquals(0, transferRepository.getTransfers().size());
        assertEquals(0, transferRepository.getMaxWeight());

    }
}
