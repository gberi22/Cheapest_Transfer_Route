package com.example.cheapesttransferroute.repository;

import com.example.cheapesttransferroute.model.Transfer;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for managing transfer data and maximum weight
 */
@Repository
@Getter
public class TransferRepository {
    private final List<Transfer> transfers = new ArrayList<>();
    private int maxWeight = 0;

    /**
     * Adds transfer to the repository
     *
     * @param transfer client given transfer
     */
    public void addTransfer(Transfer transfer) {
        transfers.add(transfer);
    }

    /**
     * Sets the maximum weight constraint in the repository
     *
     * @param maxWeight client given maximum weight
     */
    public void addMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    /**
     * Clears all the repository information
     */
    public void clearRepositoryInformation() {
        transfers.clear();
        this.maxWeight = 0;
    }
}
