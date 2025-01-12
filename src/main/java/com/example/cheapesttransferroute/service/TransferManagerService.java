package com.example.cheapesttransferroute.service;

import com.example.cheapesttransferroute.model.Transfer;
import com.example.cheapesttransferroute.model.TransferRequest;
import com.example.cheapesttransferroute.repository.TransferRepository;
import org.springframework.stereotype.Service;

@Service
public class TransferManagerService {
    private final TransferRepository transferRepository;

    public TransferManagerService(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    public void manageService(TransferRequest request) {
        for (Transfer transfer: request.getAvailableTransfers())
            transferRepository.addTransfer(transfer);
        transferRepository.addMaxWeight(request.getMaxWeight());
    }

    public void prepareService() {
        transferRepository.clearRepositoryInformation();
    }

    public boolean isAvailableTransfersEmpty(){
        return transferRepository.getTransfers().isEmpty();
    }
}
