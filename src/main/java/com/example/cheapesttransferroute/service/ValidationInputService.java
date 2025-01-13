package com.example.cheapesttransferroute.service;

import com.example.cheapesttransferroute.model.Transfer;
import com.example.cheapesttransferroute.model.TransferRequest;
import org.springframework.stereotype.Service;

@Service
public class ValidationInputService {

    public void validateTransferRequest(TransferRequest transferRequest) {
        if(transferRequest.getMaxWeight() < 1){
            throw new IllegalArgumentException("Maximum weight must be at least 1");
        }

        if(transferRequest.getAvailableTransfers().isEmpty()){
            throw new IllegalArgumentException("Available transfers list cannot be empty");
        }

        for(Transfer transfer : transferRequest.getAvailableTransfers()){
            validateTransfer(transfer);
        }
    }

    private void validateTransfer(Transfer transfer) {
        if(transfer.getWeight() < 1){
            throw new IllegalArgumentException("Weight must be at least 1");
        }

        if(transfer.getCost() < 1){
            throw new IllegalArgumentException("Cost must be at least 1");
        }
    }
}
