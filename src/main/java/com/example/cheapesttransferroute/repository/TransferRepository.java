package com.example.cheapesttransferroute.repository;

import com.example.cheapesttransferroute.model.Transfer;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Getter
public class TransferRepository {
    private final List<Transfer> transfers = new ArrayList<>();
    private int maxWeight = 0;


    public void addTransfer(Transfer transfer) {
        transfers.add(transfer);
    }

    public void addMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public void clearTransfers() {
        transfers.clear();
    }
}
